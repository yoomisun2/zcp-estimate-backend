package io.cloudzcp.estimate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cloudzcp.estimate.domain.project.Project;
import io.cloudzcp.estimate.response.EstimateHistoryResponse;
import io.cloudzcp.estimate.response.EstimateResponse;
import io.cloudzcp.estimate.response.ProjectSummaryResponse;
import io.cloudzcp.estimate.response.ProjectVolumeResponse;
import io.cloudzcp.estimate.service.ProjectService;

@RestController
@RequestMapping(value = "/project")
public class ProjectRestController {
	
	@Autowired
	private ProjectService projectService;

	@GetMapping(value = "")
	public List<ProjectSummaryResponse> getProjectList() {
		return projectService.getProjectList();
	}
	
	@PostMapping(value="")
	public Project addProject(@RequestBody Project project) {
		return projectService.addProject(project);
	}
	
	@GetMapping(value = "/{projectId}")
	public Project getProject(@PathVariable("projectId") int projectId) {
		return projectService.getProject(projectId);
	}

	@PutMapping(value = "/{projectId}")
	public Project modifyProject(@RequestBody Project project) {
		return projectService.modifyProject(project);
	}
	
	@GetMapping(value = "/{projectId}/volume")
	public ProjectVolumeResponse getProjectVolume(@PathVariable("projectId") int projectId) {
		return projectService.getVolume(projectId);
	}
	
	@PutMapping(value = "/{projectId}/volume")
	public void modifyProjectVolume(@PathVariable("projectId") int projectId, @RequestBody ProjectVolumeResponse volume) {
		projectService.modifyVolume(projectId, volume);
	}
	
	@GetMapping(value = "/{projectId}/estimate")
	public EstimateResponse getLatestEstimate(@PathVariable("projectId") int projectId) {
		return projectService.getLatestEstimate(projectId);
	}
	
	@PutMapping(value = "/{projectId}/estimate")
	public void modifyEstimateList(@PathVariable("projectId") int projectId, @RequestBody EstimateResponse estimate) {
		projectService.modifyEstimate(projectId, estimate);
	}
	
	@GetMapping(value = "/{projectId}/estimate/history")
	public List<EstimateHistoryResponse> getEstimateList(@PathVariable("projectId") int projectId) {
		return projectService.getEstimateList(projectId);
	}
	
	@GetMapping(value = "/{projectId}/estimate/history/{estimateId}")
	public EstimateResponse getEstimate(@PathVariable("estimateId") int estimateId) {
		return projectService.getEstimate(estimateId);
	}
	
	@DeleteMapping(value = "/{projectId}/estimate/history/{estimateId}")
	public void removeEstimateHistory(@PathVariable("estimateId") int estimateId) {
		projectService.deleteEstimate(estimateId);
	}
	
}
