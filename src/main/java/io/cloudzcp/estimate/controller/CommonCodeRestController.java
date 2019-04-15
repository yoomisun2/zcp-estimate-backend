package io.cloudzcp.estimate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.cloudzcp.estimate.service.CommonCodeService;

@RestController
@RequestMapping("/code")
public class CommonCodeRestController {

	@Autowired
	private CommonCodeService commonCodeService;
	
	@GetMapping(value = "/{groupName}")
	public List<Object> getStorageTypes(@PathVariable("groupName") String groupName) {
		return commonCodeService.getCommonCode(groupName);
	}
	
}
