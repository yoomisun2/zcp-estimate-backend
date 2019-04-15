package io.cloudzcp.estimate.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.constant.CommonConstants;
import io.cloudzcp.estimate.platform.domain.Template;

public class ProductTemplateResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<EstimateTemplate> cloudZService = new ArrayList<EstimateTemplate>();
	private List<EstimateTemplate> storageService = new ArrayList<EstimateTemplate>();
	
	public List<EstimateTemplate> getCloudZService() {
		return cloudZService;
	}
	public void setCloudZService(List<EstimateTemplate> cloudZService) {
		this.cloudZService = cloudZService;
	}
	public List<EstimateTemplate> getStorageService() {
		return storageService;
	}
	public void setStorageService(List<EstimateTemplate> storageService) {
		this.storageService = storageService;
	}
	
	public void addCloudZService(String serviceName, List<Template> classifications) {
		EstimateTemplate template = new EstimateTemplate();
		template.setServiceName(serviceName);
		template.setClassifications(classifications);
		cloudZService.add(template);
	}
	
	public void addStorageService(String serviceName, List<Template> classifications) {
		EstimateTemplate template = new EstimateTemplate();
		template.setServiceName(serviceName);
		template.setClassifications(classifications);
		storageService.add(template);
	}
	
	public List<Template> findCloudZServiceTemplate() {
		List<Template> templates = new ArrayList<Template>();
		cloudZService.forEach(service -> {
			service.getClassifications().forEach(template -> {
				template.setEstimateType(CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE);
				template.setServiceName(service.getServiceName());
				templates.add(template);
			});
		});
		return templates;
	}
	public List<Template> findStorageServiceTemplate() {
		List<Template> templates = new ArrayList<Template>();
		storageService.forEach(service -> {
			service.getClassifications().forEach(template -> {
				template.setEstimateType(CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE);
				template.setServiceName(service.getServiceName());
				templates.add(template);
			});
		});
		return templates;
	}
}

class EstimateTemplate {
	private String serviceName;
	private List<Template> classifications;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public List<Template> getClassifications() {
		return classifications;
	}
	public void setClassifications(List<Template> classifications) {
		this.classifications = classifications;
	}

}
