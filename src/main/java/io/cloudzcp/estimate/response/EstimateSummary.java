package io.cloudzcp.estimate.response;

import java.io.Serializable;

public class EstimateSummary implements Serializable {

	private static final long serialVersionUID = 1L;
	private String clusterName;
	private int productId;
	private String productName;
	private String estimateType;
	private int cloudCost;
	private int laborCost;
	private int totalCost;
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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

}
