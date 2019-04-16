package io.cloudzcp.estimate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cloudzcp.estimate.domain.iks.IKSStorageVersion;
import io.cloudzcp.estimate.domain.iks.IKSVmVersion;
import io.cloudzcp.estimate.response.IKSStorageResponse;
import io.cloudzcp.estimate.response.IKSVmResponse;
import io.cloudzcp.estimate.service.IKSCostsService;

@RestController
@RequestMapping(value = "/iks_costs")
public class IKSCostsRestController {

	@Autowired
	private IKSCostsService iksCostsService;
	
	@GetMapping(value = "/vm")
	public IKSVmResponse getLatestVm() {
		return iksCostsService.getLatestVm();
	}
	
	@PutMapping(value = "/vm")
	public void modifyVm(@RequestBody IKSVmResponse iksVm) {
		iksCostsService.modifyVm(iksVm);
	}
	
	@GetMapping(value = "/vm/history")
	public List<IKSVmVersion> getVmVersionList() {
		return iksCostsService.getVmVersionList();
	}
	
	@GetMapping(value = "/vm/history/{id}")
	public IKSVmResponse getVm(@PathVariable(value = "id") int id) {
		return iksCostsService.getVmVersion(id);
	}

	@GetMapping(value = "/storage")
	public IKSStorageResponse getLatestStorage() {
		return iksCostsService.getLatestStorage();
	}
	
	@PutMapping(value = "/storage")
	public void modifyStorage(@RequestBody IKSStorageResponse iksStorage) {
		iksCostsService.modifyStorage(iksStorage);
	}
	
	@GetMapping(value = "/storage/history")
	public List<IKSStorageVersion> getStorageVersionList() {
		return iksCostsService.getStorageVersionList();
	}
	
	@GetMapping(value = "/storage/history/{id}")
	public IKSStorageResponse getStorage(@PathVariable(value = "id") String id) {
		return iksCostsService.getStorage(id);
	}
}
