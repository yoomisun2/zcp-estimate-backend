package io.cloudzcp.estimate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cloudzcp.estimate.iks.domain.General;
import io.cloudzcp.estimate.service.GeneralService;

@RestController
@RequestMapping(value = "/general")
public class GeneralRestController {
	
	@Autowired
	private GeneralService generalService;

	@GetMapping
	public General getLatestGeneral() {
		return generalService.getLatestGeneral();
	}
	
	@PutMapping
	public void modifyGeneral(@RequestBody General general) {
		generalService.modifyGeneral(general);
	}
	
	@GetMapping(value = "/history")
	public List<General> getGeneralVersionList() {
		return generalService.getGeneralVersionList();
	}
	
	@GetMapping(value = "/history/{id}")
	public General getGeneral(@PathVariable(value = "id") String id) {
		return generalService.getGeneral(id);
	}
	
}
