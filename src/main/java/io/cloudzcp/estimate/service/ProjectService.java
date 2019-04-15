package io.cloudzcp.estimate.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.constant.CommonConstants;
import io.cloudzcp.estimate.exception.NoDataFoundException;
import io.cloudzcp.estimate.project.domain.EstimateItem;
import io.cloudzcp.estimate.project.domain.Project;
import io.cloudzcp.estimate.project.domain.Volumn;
import io.cloudzcp.estimate.project.mapper.EstimateItemsMapper;
import io.cloudzcp.estimate.project.mapper.EstimatesMapper;
import io.cloudzcp.estimate.project.mapper.ProjectsMapper;
import io.cloudzcp.estimate.project.mapper.VolumnsMapper;
import io.cloudzcp.estimate.response.EstimateHistoryResponse;
import io.cloudzcp.estimate.response.EstimateResponse;
import io.cloudzcp.estimate.response.EstimateSummary;
import io.cloudzcp.estimate.response.ProjectSummaryResponse;
import io.cloudzcp.estimate.response.ProjectVolumnResponse;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectsMapper projectsMapper;
	
	@Autowired
	private VolumnsMapper volumnsMapper;
	
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
			throw new NoDataFoundException(String.format("%s not found", projectId));
		}
		
		return project;
	}
	
	public Project modifyProject(Project project) {
		int count = projectsMapper.update(project);
		if(count == 0) {
			throw new NoDataFoundException(String.format("%s not found", project.getId()));
		}
		
		return project;
	}
	
	@Transactional
	public void deleteProject(int projectId) {
		estimateItemsMapper.deleteByProjectId(projectId);
		estimatesMapper.deleteByProjectId(projectId);
		volumnsMapper.deleteByProjectId(projectId);
		projectsMapper.delete(projectId);
	}
	
	public ProjectVolumnResponse getVolumn(int projectId) {
		ProjectVolumnResponse volumn = new ProjectVolumnResponse();
		List<Volumn> applications = volumnsMapper.findByProjectId(projectId);
		
		Map<String, List<Volumn>> collectorMap = applications.stream().collect(Collectors.groupingBy(Volumn::getClusterName));
		collectorMap.keySet().stream().sorted().forEach(key -> {
			volumn.addCluster(key, collectorMap.get(key));
		});
		return volumn;
	}
	
	@Transactional
	public void modifyVolumn(int projectId, ProjectVolumnResponse volumn) {
		List<Volumn> oldApplications = volumnsMapper.findByProjectId(projectId);
		List<Volumn> applications = volumn.findAllVolumn();
		
		// old application에 있는데 new application에 없으면 application delete
		if(oldApplications != null) {
			for(Volumn old : oldApplications) {
				if(!applications.stream().anyMatch(a -> a.getId() == old.getId())) {
					volumnsMapper.delete(old.getId());
				}
			}
		}
		
		for(Volumn application : applications) {
			if(application.getId() == 0) {	//id가 없으면 insert
				application.setProjectId(projectId);
				volumnsMapper.insert(application);
			} else {						//id가 있으면 update
				application.setProjectId(projectId);
				volumnsMapper.update(application);
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
		//set sub summary & structure
		List<EstimateItem> summaryItems = estimateItemsMapper.getSubSummary(result.getId());
		if(summaryItems == null || summaryItems.isEmpty()) return;
		
		EstimateItem totalSummaryItem = summaryItems.get(summaryItems.size() -1);
		result.setSumMonthly(totalSummaryItem.getPricePerMonthly());
		result.setSumYearly(totalSummaryItem.getPricePerYearly());
		summaryItems.remove(totalSummaryItem);
		
		Map<String, List<EstimateItem>> groupByMap = summaryItems.stream().collect(Collectors.groupingBy(EstimateItem::getEstimateType));
		result.addCloudZServiceStructure(groupByMap.get(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE));
		result.addStorageServiceStructure(groupByMap.get(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE));
		
		//set classification
		List<EstimateItem> items = estimateItemsMapper.findByEstimateId(result.getId());
		Map<String, List<EstimateItem>> groupByMapItems = items.stream().collect(Collectors.groupingBy(EstimateItem::getEstimateType));
		result.addPlatformServiceCostItem(groupByMapItems.get(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE));
		result.addStorageServiceItem(groupByMapItems.get(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE));
		
		// set summary
		List<EstimateSummary> summary = estimateItemsMapper.getSummary(result.getId());
		int sumCloudCost = summary.stream().mapToInt(EstimateSummary::getCloudCost).sum();
		int sumLaborCost = summary.stream().mapToInt(EstimateSummary::getLaborCost).sum();
		int sumTotalCost = summary.stream().mapToInt(EstimateSummary::getTotalCost).sum();
		result.addSummary(sumCloudCost, sumLaborCost, sumTotalCost);
		
		summary.stream().map(EstimateSummary::getClusterName).distinct().forEach(clusterName -> {
			result.addClusterSummary(clusterName,  summary.stream().filter(product -> product.getClusterName().equals(clusterName)).collect(Collectors.toList()));
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
		
		estimate.findPlatformServiceCostItem().forEach(item -> {
			item.setEstimateId(estimate.getId());
			item.setEstimateType(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE);
			estimateItemsMapper.add(item);
		});
		
		estimate.findCloudZServiceItem().forEach(item -> {
			item.setEstimateId(estimate.getId());
			item.setEstimateType(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE);
			estimateItemsMapper.add(item);
		});
	}
	
}
