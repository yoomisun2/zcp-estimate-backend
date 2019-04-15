package io.cloudzcp.estimate.response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.cloudzcp.estimate.project.domain.Estimate;
import io.cloudzcp.estimate.project.domain.EstimateItem;

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
		this.summary = new Summary();
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
	
	public List<EstimateItem> findPlatformServiceCostItem() {
		List<EstimateItem> items = new ArrayList<EstimateItem>();
		setEstimateItemProperty(cloudZService, items);
		return items;
	}
	public void addPlatformServiceCostItem(List<EstimateItem> items) {
		setEstimateItem(cloudZService, items);
	}
	
	public List<EstimateItem> findCloudZServiceItem() {
		List<EstimateItem> items = new ArrayList<EstimateItem>();
		setEstimateItemProperty(storageService, items);
		return items;
	}
	public void addStorageServiceItem(List<EstimateItem> items) {
		setEstimateItem(storageService, items);
	}
	
	public void addCloudZServiceStructure(List<EstimateItem> items) {
		cloudZService = new EstimateServiceCost();
		setStructure(cloudZService, items);
	}
	
	public void addStorageServiceStructure(List<EstimateItem> items) {
		storageService = new EstimateServiceCost();
		setStructure(storageService, items);
	}
	private void setStructure(EstimateServiceCost serviceCost, List<EstimateItem> items) {
		if(items == null) return;
		
		items.stream()
			.filter(item ->  item.getEstimateType() != null && item.getClusterName() == null)
			.collect(Collectors.toList())
			.forEach(item -> {
				serviceCost.setSumMonthly(item.getPricePerMonthly());
				serviceCost.setSumYearly(item.getPricePerYearly());
			});
	
		items.stream()
			.filter(item ->  (item.getClusterName() != null && item.getProductId() == 0))
			.collect(Collectors.toList())
			.forEach(item -> {
				EstimateCluster cluster = new EstimateCluster();
				cluster.setClusterName(item.getClusterName());
				cluster.setSumMonthly(item.getPricePerMonthly());
				cluster.setSumYearly(item.getPricePerYearly());
				serviceCost.getClusters().add(cluster);
			});
		
		items.stream()
			.filter(item ->  item.getClusterName() != null && item.getProductId() > 0 && item.getServiceName() == null)
			.forEach(item -> {
				EstimateProduct product = new EstimateProduct();
				product.setProductId(item.getProductId());
				product.setProductName(item.getProductName());
				product.setSumMonthly(item.getPricePerMonthly());
				product.setSumYearly(item.getPricePerYearly());
				
				serviceCost.getClusters().forEach(cluster -> {
					if(cluster.getClusterName().equals(item.getClusterName())) {
						cluster.getProducts().add(product);
					}
				});
			});

		items.stream()
			.filter(item ->  item.getClusterName() != null && item.getProductId() > 0 && item.getServiceName() != null)
			.forEach(item -> {
				EstimateService service = new EstimateService();
				service.setServiceName(item.getServiceName());
				service.setSumMonthly(item.getPricePerMonthly());
				service.setSumYearly(item.getPricePerYearly());
				
				serviceCost.getClusters().forEach(cluster -> {
					if(cluster.getClusterName().equals(item.getClusterName())) {
						cluster.getProducts().forEach(product -> {
							if(product.getProductId() == item.getProductId()) {
								product.getServices().add(service);
							}
						});
					}
				});
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

	public void setEstimateItem(EstimateServiceCost serviceCost, List<EstimateItem> items) {
		if(items == null) return;
		
		items.forEach(item -> {
			serviceCost.getClusters().stream()
				.filter(cluster -> cluster.getClusterName().equals(item.getClusterName()))
				.findFirst().ifPresent(cluster -> {
					cluster.getProducts().stream()
						.filter(product -> product.getProductId() == item.getProductId())
						.findFirst().ifPresent(product ->{
							product.getServices().stream()
								.filter(service -> service.getServiceName().equals(item.getServiceName()))
								.findFirst().ifPresent(service -> {
									service.addEstimateItem(item);
								});
						});
				});;
		});
	}
}

class EstimateServiceCost {
	private int sumMonthly;
	private int sumYearly;
	private List<EstimateCluster> clusters = new ArrayList<EstimateCluster>();
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

