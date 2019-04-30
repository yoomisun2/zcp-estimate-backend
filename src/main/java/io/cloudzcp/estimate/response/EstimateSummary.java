package io.cloudzcp.estimate.response;

import java.io.Serializable;

public class EstimateSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	private String environmentName;
	private int productId;
	private String productName;
	private String estimateType;
	private int cloudCost;
	private int laborCost;
	private int totalCost;
	private int pricePerGb;
	
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getCloudCost() {
		return cloudCost;
	}
	public void setCloudCost(int cloudCost) {
		this.cloudCost = cloudCost;
	}
	public int getLaborCost() {
		return laborCost;
	}
	public void setLaborCost(int laborCost) {
		this.laborCost = laborCost;
	}
	public int getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getEstimateType() {
		return estimateType;
	}
	public void setEstimateType(String estimateType) {
		this.estimateType = estimateType;
	}
	public int getPricePerGb() {
		return pricePerGb;
	}
	public void setPricePerGb(int pricePerGb) {
		this.pricePerGb = pricePerGb;
	}
	
}
