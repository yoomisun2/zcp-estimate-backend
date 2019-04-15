package io.cloudzcp.estimate.response;

import io.cloudzcp.estimate.project.domain.Estimate;

public class EstimateHistoryResponse extends Estimate {

	private static final long serialVersionUID = 1L;
	private int cloudZServiceMonthlyPrice;
	private int cloudZServiceYearlyPrice;
	private int storageServiceMonthlyPrice;
	private int storageServiceYearlyPrice;
	
	public int getCloudZServiceMonthlyPrice() {
		return cloudZServiceMonthlyPrice;
	}
	public void setCloudZServiceMonthlyPrice(int cloudZServiceMonthlyPrice) {
		this.cloudZServiceMonthlyPrice = cloudZServiceMonthlyPrice;
	}
	public int getCloudZServiceYearlyPrice() {
		return cloudZServiceYearlyPrice;
	}
	public void setCloudZServiceYearlyPrice(int cloudZServiceYearlyPrice) {
		this.cloudZServiceYearlyPrice = cloudZServiceYearlyPrice;
	}
	public int getStorageServiceMonthlyPrice() {
		return storageServiceMonthlyPrice;
	}
	public void setStorageServiceMonthlyPrice(int storageServiceMonthlyPrice) {
		this.storageServiceMonthlyPrice = storageServiceMonthlyPrice;
	}
	public int getStorageServiceYearlyPrice() {
		return storageServiceYearlyPrice;
	}
	public void setStorageServiceYearlyPrice(int storageServiceYearlyPrice) {
		this.storageServiceYearlyPrice = storageServiceYearlyPrice;
	}
}
