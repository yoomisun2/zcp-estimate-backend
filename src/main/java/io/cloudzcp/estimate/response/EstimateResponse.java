package io.cloudzcp.estimate.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.cloudzcp.estimate.domain.project.Estimate;
import io.cloudzcp.estimate.domain.project.EstimateItem;

public class EstimateResponse extends Estimate {

	private static final long serialVersionUID = 1L;

	private int sumMonthly;
	private int sumYearly;
	private EstimateServiceCost cloudZService = new EstimateServiceCost();
	private EstimateServiceCost storageService = new EstimateServiceCost();
	private Summary summary = new Summary();
	
	public int getSumMonthly() {
		return sumMonthly;
	}
	public void setSumMonthly(int sumMonthly) {
		this.sumMonthly = sumMonthly;
	}
	public int getSumYearly() {
		return sumYearly;
	}
	public void setSumYearly(int sumYearly) {
		this.sumYearly = sumYearly;
	}
	public EstimateServiceCost getCloudZService() {
		return cloudZService;
	}
	public void setCloudZService(EstimateServiceCost cloudZService) {
		this.cloudZService = cloudZService;
	}
	public EstimateServiceCost getStorageService() {
		return storageService;
	}
	public void setStorageService(EstimateServiceCost storageService) {
		this.storageService = storageService;
	}
	public Summary getSummary() {
		return summary;
	}
	public void setSummary(Summary summary) {
		this.summary = summary;
	}
	
	public void addSummary(int sumCloudCost, int sumLaborCost, int sumTotalCost) {
		this.summary.setSumCloudCost(sumCloudCost);
		this.summary.setSumLaborCost(sumLaborCost);
		this.summary.setSumTotalCost(sumTotalCost);
	}
	public void addClusterSummary(String clusterName, List<EstimateSummary> products) {
		ClusterSummary sum = new ClusterSummary();
		sum.setClusterName(clusterName);
		sum.setProducts(products);
		this.summary.getClusters().add(sum);
	}
	
	public List<EstimateItem> findCloudZServiceItem() {
		List<EstimateItem> items = new ArrayList<EstimateItem>();
		setEstimateItemProperty(cloudZService, items);
		return items;
	}
	
	public List<EstimateItem> findStorageServiceItem() {
		List<EstimateItem> items = new ArrayList<EstimateItem>();
		setEstimateItemProperty(storageService, items);
		return items;
	}
	
	public void addCloudZService(List<EstimateItem> items) {
		addEstimateServiceCost(cloudZService, items);
	}
	
	public void addStorageService(List<EstimateItem> items) {
		addEstimateServiceCost(storageService, items);
	}
	
	private void addEstimateServiceCost(EstimateServiceCost estimateType, List<EstimateItem> items) {
		estimateType.setSumMonthly(items.stream().mapToInt(EstimateItem::getPricePerMonthly).sum());
		estimateType.setSumYearly(items.stream().mapToInt(EstimateItem::getPricePerYearly).sum());
		items.stream().map(EstimateItem::getClusterName)
					  .distinct()
					  .forEach(clusterName -> {
						  estimateType.addCluster(clusterName, items.stream().filter(item -> clusterName.equals(item.getClusterName())).collect(Collectors.toList()));
					  });
	}
	
	private void setEstimateItemProperty(EstimateServiceCost serviceCost, List<EstimateItem> items) {
		serviceCost.getClusters().forEach(cluster -> {
			cluster.getProducts().forEach(product -> {
				product.getServices().forEach(service -> {
					service.getClassifications().forEach(item -> {
						item.setServiceName(service.getServiceName());
						item.setProductId(product.getProductId());
						item.setProductName(product.getProductName());
						item.setClusterName(cluster.getClusterName());
						items.add(item);
					});
				});
			});
		});
	}
}

class EstimateServiceCost {
	private int sumMonthly;
	private int sumYearly;
	private List<EstimateCluster> clusters = new ArrayList<EstimateCluster>();
	
	public void addCluster(String clusterName, List<EstimateItem> items) {
		EstimateCluster cluster = new EstimateCluster();
		cluster.setClusterName(clusterName);
		cluster.setSumMonthly(items.stream().mapToInt(EstimateItem::getPricePerMonthly).sum());
		cluster.setSumYearly(items.stream().mapToInt(EstimateItem::getPricePerYearly).sum());
		
		items.stream().map(EstimateItem::getProductId)
		  			  .distinct()
		  			  .forEach(productId -> {
		  				  cluster.addProduct(productId, items.stream().filter(item -> productId == item.getProductId()).collect(Collectors.toList()));
		  			  });
		
		clusters.add(cluster);
	}
	public int getSumMonthly() {
		return sumMonthly;
	}
	public void setSumMonthly(int sumMonthly) {
		this.sumMonthly = sumMonthly;
	}
	public int getSumYearly() {
		return sumYearly;
	}
	public void setSumYearly(int sumYearly) {
		this.sumYearly = sumYearly;
	}
	public List<EstimateCluster> getClusters() {
		return clusters;
	}
	public void setClusters(List<EstimateCluster> clusters) {
		this.clusters = clusters;
	}
}

class EstimateCluster {
	private String clusterName;
	private int sumMonthly;
	private int sumYearly;
	private List<EstimateProduct> products = new ArrayList<EstimateProduct>();
	
	public void addProduct(int productId, List<EstimateItem> items) {
		EstimateProduct product = new EstimateProduct();
		product.setProductId(productId);
		product.setProductName(items.get(0).getProductName());
		product.setSumMonthly(items.stream().mapToInt(EstimateItem::getPricePerMonthly).sum());
		product.setSumYearly(items.stream().mapToInt(EstimateItem::getPricePerYearly).sum());
		
		items.stream().map(EstimateItem::getServiceName)
					  .distinct()
					  .forEach(serviceName -> {
						  product.addService(serviceName, items.stream().filter(item -> serviceName.equals(item.getServiceName())).collect(Collectors.toList()));
					  });
		
		products.add(product);
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public int getSumMonthly() {
		return sumMonthly;
	}
	public void setSumMonthly(int sumMonthly) {
		this.sumMonthly = sumMonthly;
	}
	public int getSumYearly() {
		return sumYearly;
	}
	public void setSumYearly(int sumYearly) {
		this.sumYearly = sumYearly;
	}
	public List<EstimateProduct> getProducts() {
		return products;
	}
	public void setProducts(List<EstimateProduct> products) {
		this.products = products;
	}
}

class EstimateProduct {
	private int productId;
	private String productName;
	private int sumMonthly;
	private int sumYearly;
	private List<EstimateService> services = new ArrayList<EstimateService>();
	
	public void addService(String serviceName, List<EstimateItem> items) {
		EstimateService service = new EstimateService();
		service.setServiceName(serviceName);
		service.setSumMonthly(items.stream().mapToInt(EstimateItem::getPricePerMonthly).sum());
		service.setSumYearly(items.stream().mapToInt(EstimateItem::getPricePerYearly).sum());
		service.setClassifications(items);
		services.add(service);
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getSumMonthly() {
		return sumMonthly;
	}
	public void setSumMonthly(int sumMonthly) {
		this.sumMonthly = sumMonthly;
	}
	public int getSumYearly() {
		return sumYearly;
	}
	public void setSumYearly(int sumYearly) {
		this.sumYearly = sumYearly;
	}
	public List<EstimateService> getServices() {
		return services;
	}
	public void setServices(List<EstimateService> services) {
		this.services = services;
	}
}

class EstimateService {
	private String serviceName;
	private int sumMonthly;
	private int sumYearly;
	private List<EstimateItem> classifications = new ArrayList<EstimateItem>();
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getSumMonthly() {
		return sumMonthly;
	}
	public void setSumMonthly(int sumMonthly) {
		this.sumMonthly = sumMonthly;
	}
	public int getSumYearly() {
		return sumYearly;
	}
	public void setSumYearly(int sumYearly) {
		this.sumYearly = sumYearly;
	}
	public List<EstimateItem> getClassifications() {
		return classifications;
	}
	public void setClassifications(List<EstimateItem> classifications) {
		this.classifications = classifications;
	}
	public void addEstimateItem(EstimateItem item) {
		classifications.add(item);
	}
}

class ClusterSummary {
	private String clusterName;
	List<EstimateSummary> products = new ArrayList<EstimateSummary>();
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public List<EstimateSummary> getProducts() {
		return products;
	}
	public void setProducts(List<EstimateSummary> products) {
		this.products = products;
	}
	
}

class Summary {
	private int sumCloudCost;
	private int sumLaborCost;
	private int sumTotalCost;
	private List<ClusterSummary> clusters = new ArrayList<ClusterSummary>();
	public int getSumCloudCost() {
		return sumCloudCost;
	}
	public void setSumCloudCost(int sumCloudCost) {
		this.sumCloudCost = sumCloudCost;
	}
	public int getSumLaborCost() {
		return sumLaborCost;
	}
	public void setSumLaborCost(int sumLaborCost) {
		this.sumLaborCost = sumLaborCost;
	}
	public int getSumTotalCost() {
		return sumTotalCost;
	}
	public void setSumTotalCost(int sumTotalCost) {
		this.sumTotalCost = sumTotalCost;
	}
	public List<ClusterSummary> getClusters() {
		return clusters;
	}
	public void setClusters(List<ClusterSummary> clusters) {
		this.clusters = clusters;
	}
	
}

