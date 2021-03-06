package io.cloudzcp.estimate.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.constant.CommonConstants;
import io.cloudzcp.estimate.domain.platform.Addon;
import io.cloudzcp.estimate.domain.platform.MspCost;
import io.cloudzcp.estimate.domain.platform.MspCostVersion;
import io.cloudzcp.estimate.domain.platform.Product;
import io.cloudzcp.estimate.domain.platform.Template;
import io.cloudzcp.estimate.exception.EntityNotFoundException;
import io.cloudzcp.estimate.mapper.platform.AddonsMapper;
import io.cloudzcp.estimate.mapper.platform.MspCostVersionsMapper;
import io.cloudzcp.estimate.mapper.platform.MspCostsMapper;
import io.cloudzcp.estimate.mapper.platform.ProductsMapper;
import io.cloudzcp.estimate.mapper.platform.TemplatesMapper;
import io.cloudzcp.estimate.response.ProductMspCost;
import io.cloudzcp.estimate.response.ProductMspCostVersion;
import io.cloudzcp.estimate.response.ProductTemplateResponse;
import io.cloudzcp.estimate.response.AddonResponse;

@Service
public class PlatformService {

	@Autowired
	private ProductsMapper productsMapper;
	
	@Autowired
	private AddonsMapper addonsMapper;
	
	@Autowired
	private TemplatesMapper templatesMapper;
	
	@Autowired
	private MspCostVersionsMapper mspCostVersionsMapper;
	
	@Autowired
	private MspCostsMapper mspCostsMapper;
	
	public List<ProductMspCost> getProductList() {
		return productsMapper.findAll();
	}
	
	public Product getProduct(int productId) {
		Product productService = productsMapper.findById(productId);
		if(productService == null) {
			throw new EntityNotFoundException(String.format("Product is not found : %s", productId));
		}
		return productService;
	}
	
	@Transactional
	public Product addProduct(Product product) {
		productsMapper.add(product);
		return productsMapper.findById(product.getId());
	}
	
	@Transactional
	public void removeProduct(int productId) {
		addonsMapper.deleteByProductId(productId);
		templatesMapper.deleteByProductId(productId);
		mspCostsMapper.deleteByProductId(productId);
		int count = productsMapper.delete(productId);
		
		if(count == 0) {
			throw new EntityNotFoundException(String.format("Product is not found : %s", productId));
		}
	}
	
	@Transactional
	public void modifyProduct(Product productService) {
		int count = productsMapper.update(productService);
		if(count == 0) {
			throw new EntityNotFoundException(String.format("Product is not found : %s", productService.getId()));
		}
	}
	
	public List<AddonResponse> getServiceApplicationList(int productId) {
		List<AddonResponse> services = addonsMapper.findServiceSummaryByProductId(productId);
		List<Addon> list = addonsMapper.findByProductId(productId);
		
		for(AddonResponse service : services) {
			service.setApplications(list.stream().filter(template -> service.getServiceName().equals(template.getServiceName())).collect(Collectors.toList()));
		}

		return services;
	}
	
	@Transactional
	public void modifyServiceApplicationList(int productId, List<AddonResponse> list) {
		List<Addon> oldList = addonsMapper.findByProductId(productId);
		List<Addon> applications = new ArrayList<Addon>();
		
		// set volumns_id, cluster_name
		for(AddonResponse response : list) {
			response.getApplications().forEach(template -> {
				template.setProductId(productId);
				template.setServiceName(response.getServiceName());
			});
			
			applications.addAll(response.getApplications());
		}

		// old application에 있는데 new application에 없으면 application delete
		if(oldList != null) {
			for(Addon old : oldList) {
				if(!applications.stream().anyMatch(a -> a.getId() == old.getId())) {
					addonsMapper.delete(old.getId());
				}
			}
		}
		
		for(Addon application : applications) {
			if(application.getId() == 0) {	//id가 없으면 insert
				addonsMapper.insert(application);
			} else {						//id가 있으면 update
				addonsMapper.update(application);
			}
		}
	}


	public ProductTemplateResponse getTemplateList(int productId) {
		ProductTemplateResponse response = new ProductTemplateResponse();
		
		List<Template> list = templatesMapper.findByProductId(productId);
		if(list == null || list.isEmpty())
			return response;
		
		list.stream().filter(template -> CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE.equals(template.getEstimateType()))
						  .map(Template::getServiceName)
						  .distinct()
						  .forEach(serviceName -> {
			response.addCloudZService(serviceName, 
					list.stream().filter(template -> CommonConstants.ESTIMATE_TYPE_CLOUDZ_SERVICE.equals(template.getEstimateType()) && serviceName.equals(template.getServiceName()))
									  .collect(Collectors.toList()));
		});
		
		list.stream().filter(template -> CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE.equals(template.getEstimateType()))
						  .map(Template::getServiceName)
						  .distinct()
						  .forEach(serviceName -> {
			response.addStorageService(serviceName, 
					list.stream().filter(template -> CommonConstants.ESTIMATE_TYPE_STORAGE_SERVICE.equals(template.getEstimateType()) && serviceName.equals(template.getServiceName()))
									  .collect(Collectors.toList()));
		});

		return response;
	}
	
	@Transactional
	public void modifyTemplate(int productId, ProductTemplateResponse response) {
		List<Template> classifications = new ArrayList<Template>();
		classifications.addAll(response.findCloudZServiceTemplate());
		classifications.addAll(response.findStorageServiceTemplate());
		
		List<Template> oldList = templatesMapper.findByProductId(productId);
		
		// old application에 있는데 new application에 없으면 application delete
		if(oldList != null) {
			for(Template old : oldList) {
				if(!classifications.stream().anyMatch(a -> a.getId() == old.getId())) {
					templatesMapper.delete(old.getId());
				}
			}
		}
		
		int order = 1;
		for(Template classification : classifications) {
			classification.setProductId(productId);
			classification.setSort(order++);
			
			if(classification.getId() == 0) {	//id가 없으면 insert
				templatesMapper.insert(classification);
			} else {						//id가 있으면 update
				templatesMapper.update(classification);
			}
		}
	}
	
	public ProductMspCostVersion getLatestMspCost() {
		ProductMspCostVersion mspCostVersion = mspCostVersionsMapper.findByLastVersion();
		if(mspCostVersion == null) {
			mspCostVersion = new ProductMspCostVersion();
		}
		
		return getProductMspCostVersion(mspCostVersion);
	}
	
	private ProductMspCostVersion getProductMspCostVersion(ProductMspCostVersion productMspCostVersion) {
		List<ProductMspCost> productMspCostList = productsMapper.findAll();
		List<MspCost> mspCostList = mspCostsMapper.findByVersionId(productMspCostVersion.getId());
		
		Map<Integer, List<MspCost>> collectorMap = mspCostList.stream().collect(Collectors.groupingBy(MspCost::getProductId));
		
		productMspCostList.forEach(productMspCost -> {
			if(collectorMap.containsKey(productMspCost.getId())) {
				productMspCost.setMspCosts(collectorMap.get(productMspCost.getId()));
			}
		});

		productMspCostVersion.setProducts(productMspCostList);
		
		return productMspCostVersion;
	}
	
	@Transactional
	public void modifyMspCost(ProductMspCostVersion mspCostVersion) {
		MspCostVersion lastVersion = mspCostVersionsMapper.findByLastVersion();
		mspCostVersion.setVersion(lastVersion == null ? 1 : lastVersion.getVersion() + 1);
		mspCostVersionsMapper.add(mspCostVersion);
		
		if(mspCostVersion.getProducts() != null) {
			for(ProductMspCost productMspCost : mspCostVersion.getProducts()) {
				productMspCost.getMspCosts().forEach(mspCost -> {
					mspCost.setProductId(productMspCost.getId());
					mspCost.setMspCostVersionId(mspCostVersion.getId());
					mspCostsMapper.add(mspCost);
				});
			}
		}
	}
	
	public List<MspCostVersion> getMspCostVersionList() {
		return mspCostVersionsMapper.findAll();
	}
	
	public ProductMspCostVersion getMspCostVersion(int id) {
		ProductMspCostVersion mspCostVersion = mspCostVersionsMapper.findById(id);
		if(mspCostVersion == null) {
			throw new EntityNotFoundException(String.format("Msp cost is not found : %s", id));
		}
		
		return getProductMspCostVersion(mspCostVersion);
	}
	
}
