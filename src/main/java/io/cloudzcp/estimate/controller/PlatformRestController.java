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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cloudzcp.estimate.response.ProductMspCost;
import io.cloudzcp.estimate.response.ProductMspCostVersion;
import io.cloudzcp.estimate.response.ProductTemplateResponse;
import io.cloudzcp.estimate.platform.domain.MspCostVersion;
import io.cloudzcp.estimate.platform.domain.Product;
import io.cloudzcp.estimate.response.AddonResponse;
import io.cloudzcp.estimate.service.PlatformService;

@RestController
@RequestMapping(value = "/platform")
public class PlatformRestController {
	
	@Autowired
	private PlatformService platformService;
	
	@GetMapping(value = "/product")
	public List<ProductMspCost> getProductList() {
		return platformService.getProductList();
	}
	
	@PostMapping(value = "/product")
	public Product addProduct(@RequestBody Product product) {
		return platformService.addProduct(product);
	}
	
	@GetMapping(value = "/product/{productId}")
	public Product getProduct(@PathVariable(value = "productId") int productId) {
		return platformService.getProduct(productId);
	}
	
	@DeleteMapping(value = "/product/{productId}")
	public void removeProduct(@PathVariable(value = "productId") int productId) {
		platformService.removeProduct(productId);
	}

	@PutMapping(value = "/product/{productId}")
	public void modifyProduct(@PathVariable(value = "productId") int productId, @RequestBody Product product) {
		platformService.modifyProduct(product);
	}
	
	@GetMapping(value = "/product/{productId}/service")
	public List<AddonResponse> getServiceList(@PathVariable(value = "productId") int productId) {
		return platformService.getServiceApplicationList(productId);
	}
	
	@PutMapping(value = "/product/{productId}/service")
	public void modifyServiceApplicationList(@PathVariable(value = "productId") int productId, @RequestBody List<AddonResponse> services) {
		platformService.modifyServiceApplicationList(productId, services);
	}
	

	@GetMapping(value = "/product/{productId}/template")
	public ProductTemplateResponse getTemplateList(@PathVariable(value = "productId") int productId) {
		return platformService.getTemplateList(productId);
	}
	
	@PutMapping(value = "/product/{productId}/template")
	public void modifyTemplateList(@PathVariable(value = "productId") int productId, @RequestBody ProductTemplateResponse response) {
		platformService.modifyTemplate(productId, response);
	}
	
	@GetMapping(value = "/msp")
	public ProductMspCostVersion getLatestMspCost() {
		return platformService.getLatestMspCost();
	}
	
	@PutMapping(value = "/msp")
	public void modifyMspCost(@RequestBody ProductMspCostVersion mspCostVersion) {
		platformService.modifyMspCost(mspCostVersion);
	}
	
	@GetMapping(value = "/msp/history")
	public List<MspCostVersion> getMspCostVersionList() {
		return platformService.getMspCostVersionList();
	}
	
	@GetMapping(value = "/msp/history/{id}")
	public ProductMspCostVersion getMspCost(@PathVariable(value = "id") int id) {
		return platformService.getMspCostVersion(id);
	}

	
}
