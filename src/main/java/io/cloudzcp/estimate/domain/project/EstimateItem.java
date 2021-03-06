package io.cloudzcp.estimate.domain.project;

import java.io.Serializable;

public class EstimateItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int estimateId;
	private String estimateType;
	private int environmentId;
	private String environmentName;
	private int productId;
	private String productName;
	private String serviceName;
	private String classificationName;
	private String classificationType;
	private int addonId;
	private String addonApplicationName;
	private int iksVmId;
	private String iksVmName;
	private String hardwareType;
	private String storageType;
	private int enduranceIops;
	private int iksFileStorageId;
	private int iksFileStorageDisk;
	private int number;
	private int cores;
	private int memory;
	private int pricePerMonthly;
	private int pricePerYearly;
	private int sort;
	private String created;
	private String createdDt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEstimateId() {
		return estimateId;
	}
	public void setEstimateId(int estimateId) {
		this.estimateId = estimateId;
	}
	public String getEstimateType() {
		return estimateType;
	}
	public void setEstimateType(String estimateType) {
		this.estimateType = estimateType;
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
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getClassificationName() {
		return classificationName;
	}
	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}
	public String getClassificationType() {
		return classificationType;
	}
	public void setClassificationType(String classificationType) {
		this.classificationType = classificationType;
	}
	public int getIksVmId() {
		return iksVmId;
	}
	public void setIksVmId(int iksVmId) {
		this.iksVmId = iksVmId;
	}
	public String getIksVmName() {
		return iksVmName;
	}
	public void setIksVmName(String iksVmName) {
		this.iksVmName = iksVmName;
	}
	public String getHardwareType() {
		return hardwareType;
	}
	public void setHardwareType(String hardwareType) {
		this.hardwareType = hardwareType;
	}
	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}
	public int getEnduranceIops() {
		return enduranceIops;
	}
	public void setEnduranceIops(int enduranceIops) {
		this.enduranceIops = enduranceIops;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getCores() {
		return cores;
	}
	public void setCores(int cores) {
		this.cores = cores;
	}
	public int getMemory() {
		return memory;
	}
	public void setMemory(int memory) {
		this.memory = memory;
	}
	public int getPricePerMonthly() {
		return pricePerMonthly;
	}
	public void setPricePerMonthly(int pricePerMonthly) {
		this.pricePerMonthly = pricePerMonthly;
	}
	public int getPricePerYearly() {
		return pricePerYearly;
	}
	public void setPricePerYearly(int pricePerYearly) {
		this.pricePerYearly = pricePerYearly;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreatedDt() {
		return createdDt;
	}
	public void setCreatedDt(String createdDt) {
		this.createdDt = createdDt;
	}
	public int getAddonId() {
		return addonId;
	}
	public void setAddonId(int addonId) {
		this.addonId = addonId;
	}
	public String getAddonApplicationName() {
		return addonApplicationName;
	}
	public void setAddonApplicationName(String addonApplicationName) {
		this.addonApplicationName = addonApplicationName;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public int getEnvironmentId() {
		return environmentId;
	}
	public void setEnvironmentId(int environmentId) {
		this.environmentId = environmentId;
	}
	public String getEnvironmentName() {
		return environmentName;
	}
	public void setEnvironmentName(String environmentName) {
		this.environmentName = environmentName;
	}
	public int getIksFileStorageId() {
		return iksFileStorageId;
	}
	public void setIksFileStorageId(int iksFileStorageId) {
		this.iksFileStorageId = iksFileStorageId;
	}
	public int getIksFileStorageDisk() {
		return iksFileStorageDisk;
	}
	public void setIksFileStorageDisk(int iksFileStorageDisk) {
		this.iksFileStorageDisk = iksFileStorageDisk;
	}
	
}
