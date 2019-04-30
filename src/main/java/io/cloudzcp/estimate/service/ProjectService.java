package io.cloudzcp.estimate.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.constant.CommonConstants;
import io.cloudzcp.estimate.domain.project.Environment;
import io.cloudzcp.estimate.domain.project.EstimateItem;
import io.cloudzcp.estimate.domain.project.Project;
import io.cloudzcp.estimate.domain.project.Volume;
import io.cloudzcp.estimate.exception.EntityNotFoundException;
import io.cloudzcp.estimate.mapper.project.EnvironmentsMapper;
import io.cloudzcp.estimate.mapper.project.EstimateItemsMapper;
import io.cloudzcp.estimate.mapper.project.EstimatesMapper;
import io.cloudzcp.estimate.mapper.project.ProjectsMapper;
import io.cloudzcp.estimate.mapper.project.VolumesMapper;
import io.cloudzcp.estimate.response.EstimateHistoryResponse;
import io.cloudzcp.estimate.response.EstimateResponse;
import io.cloudzcp.estimate.response.EstimateSummary;
import io.cloudzcp.estimate.response.ProjectSummaryResponse;
import io.cloudzcp.estimate.response.ProjectVolumeResponse;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectsMapper projectsMapper;
	
	@Autowired
	private EnvironmentsMapper environmentsMapper;
	
	@Autowired
	private VolumesMapper volumesMapper;
	
	@Autowired
	private EstimatesMapper estimatesMapper;
	
	@Autowired
	private EstimateItemsMapper estimateItemsMapper;
	
	public List<ProjectSummaryResponse> getProjectList() {
		List<ProjectSummaryResponse> response = projectsMapper.findAll();
		return response;
	}
	
	public Project addProject(Project project) {
		projectsMapper.insert(project);
		return getProject(project.getId());
	}
	
	public Project getProject(int projectId) {
		Project project = projectsMapper.findById(projectId);
		if(project == null) {
			throw new EntityNotFoundException(String.format("Project is not found : %s", projectId));
		}
		
		return project;
	}
	
	public Project modifyProject(Project project) {
		int count = projectsMapper.update(project);
		if(count == 0) {
			throw new EntityNotFoundException(String.format("Project is not found : %s", project.getId()));
		}
		
		return project;
	}
	
	@Transactional
	public void deleteProject(int projectId) {
		estimateItemsMapper.deleteByProjectId(projectId);
		estimatesMapper.deleteByProjectId(projectId);
		volumesMapper.deleteByProjectId(projectId);
		environmentsMapper.deleteByProjectId(projectId);
		projectsMapper.delete(projectId);
	}
	
	public ProjectVolumeResponse getVolume(int projectId) {
		ProjectVolumeResponse response = new ProjectVolumeResponse();
		
		List<Environment> environments = environmentsMapper.findByProjectId(projectId);
		List<Volume> applications = volumesMapper.findByProjectId(projectId);
		
		Map<Integer, List<Volume>> collectorMap = applications.stream().collect(Collectors.groupingBy(Volume::getEnvironmentId));

		response.setSumCpu(applications.stream().mapToDouble(Volume::getPodCpuLimitSum).sum()/1000);
		response.setSumMemory(Math.ceil(applications.stream().mapToDouble(Volume::getPodMemoryLimitSum).sum()/1024));
		
		environments.forEach(cluster -> {
			response.addCluster(cluster.getId(), cluster.getName(), collectorMap.get(cluster.getId()));
		});
		return response;
	}
	
	@Transactional
	public void modifyVolume(int projectId, ProjectVolumeResponse response) {
		List<Environment> oldEnvironments = environmentsMapper.findByProjectId(projectId);
		List<Volume> oldApplications = volumesMapper.findByProjectId(projectId);
		
		List<Environment> environments = response.findAllEnvironment();
		List<Volume> applications = response.findAllVolume();
		
		// old application에 있는데 new application에 없으면 application delete
		if(oldApplications != null) {
			for(Volume old : oldApplications) {
				if(!applications.stream().anyMatch(a -> a.getId() == old.getId())) {
					volumesMapper.delete(old.getId());
				}
			}
		}
		
		//old environment에 있는데 new environment에 없으면 delete
		if(oldEnvironments != null) {
			for(Environment old : oldEnvironments) {
				if(!environments.stream().anyMatch(e -> e.getId() == old.getId())) {
					environmentsMapper.delete(old.getId());
				}
			}
		}
		
		for(Environment env : environments) {
			env.setProjectId(projectId);
			
			if(env.getId() == 0) { // environment insert
				environmentsMapper.insert(env);
				
				List<Volume> list = response.findVolume(env);
				for(Volume volume : list) {
					volume.setEnvironmentId(env.getId());
					volumesMapper.insert(volume);
				}
			} else { 				// environment update
				environmentsMapper.update(env);

				List<Volume> list = response.findVolume(env);
				for(Volume volume : list) {
					volume.setEnvironmentId(env.getId());

					if(volume.getId() == 0) {	// volume insert
						volumesMapper.insert(volume);
					} else {					// volume update
						volumesMapper.update(volume);
					}
				}
			}
		}
	}
	
	public EstimateResponse getLatestEstimate(int projectId) {
		EstimateResponse result = estimatesMapper.findByLastVersion(projectId);
		if(result != null) {
			setEstimateItemList(result);
		} else {
			result = new EstimateResponse();
		}
		
		return result;
	}
	
	private void setEstimateItemList(EstimateResponse result) {
		List<EstimateItem> list = estimateItemsMapper.findByEstimateId(result.getId());
		Map<String, List<EstimateItem>> groupByMap = list.stream().collect(Collectors.groupingBy(EstimateItem::getEstimateType));
		
		result.setSumMonthly(list.stream().mapToInt(EstimateItem::getPricePerMonthly).sum());
		result.setSumYearly(list.stream().mapToInt(EstimateItem::getPricePerYearly).sum());
		result.addCloudZService(groupByMap.get(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE));
		result.addStorageService(groupByMap.get(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE));
		
		// set summary
		List<EstimateSummary> summary = estimateItemsMapper.getSummary(result.getId());
		int sumCloudCost = summary.stream().mapToInt(EstimateSummary::getCloudCost).sum();
		int sumLaborCost = summary.stream().mapToInt(EstimateSummary::getLaborCost).sum();
		int sumTotalCost = summary.stream().mapToInt(EstimateSummary::getTotalCost).sum();
		result.addSummary(sumCloudCost, sumLaborCost, sumTotalCost);
		
		summary.stream().map(EstimateSummary::getEnvironmentName).distinct().forEach(clusterName -> {
			result.addEnvironmentSummary(clusterName,  summary.stream().filter(product -> product.getEnvironmentName().equals(clusterName)).collect(Collectors.toList()));
		});
	}
	
	public List<EstimateHistoryResponse> getEstimateList(int projectId) {
		return estimatesMapper.findByProjectId(projectId);
	}
	
	public EstimateResponse getEstimate(int estimateId) {
		EstimateResponse result = estimatesMapper.findById(estimateId);
		if(result != null) {
			setEstimateItemList(result);
		}
		
		return result;
	}
	
	@Transactional
	public void deleteEstimate(int estimateId) {
		estimateItemsMapper.deleteByEstimateId(estimateId);
		estimatesMapper.delete(estimateId);
	}
	
	@Transactional
	public void modifyEstimate(int projectId, EstimateResponse estimate) {
		EstimateResponse lastVersion = estimatesMapper.findByLastVersion(projectId);
		estimate.setProjectId(projectId);
		estimate.setVersion(lastVersion == null ? 1 : lastVersion.getVersion() + 1);
		estimatesMapper.add(estimate);
		
		int sort = 1;
		for(EstimateItem item : estimate.findCloudZServiceItem()) {
			item.setEstimateId(estimate.getId());
			item.setEstimateType(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE);
			item.setSort(sort++);
			estimateItemsMapper.add(item);
		}
		
		for(EstimateItem item : estimate.findStorageServiceItem()) {
			item.setEstimateId(estimate.getId());
			item.setEstimateType(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE);
			item.setSort(sort++);
			estimateItemsMapper.add(item);
		}
	}
	
}
