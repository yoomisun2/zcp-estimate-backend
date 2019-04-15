package io.cloudzcp.estimate.iks.domain;

import java.io.Serializable;

public class General implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int version;
	private float ibmDcRate;
	private int platformCpuPerWorker;
	private int platformMemoryPerWorker;
	private int exchangeRate;
	private int ipAllocation;
	private String description;
	private String created;
	private String createdDt;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public float getIbmDcRate() {
		return ibmDcRate;
	}
	public void setIbmDcRate(float ibmDcRate) {
		this.ibmDcRate = ibmDcRate;
	}
	public int getPlatformCpuPerWorker() {
		return platformCpuPerWorker;
	}
	public void setPlatformCpuPerWorker(int platformCpuPerWorker) {
		this.platformCpuPerWorker = platformCpuPerWorker;
	}
	public int getPlatformMemoryPerWorker() {
		return platformMemoryPerWorker;
	}
	public void setPlatformMemoryPerWorker(int platformMemoryPerWorker) {
		this.platformMemoryPerWorker = platformMemoryPerWorker;
	}
	public int getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(int exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getIpAllocation() {
		return ipAllocation;
	}
	public void setIpAllocation(int ipAllocation) {
		this.ipAllocation = ipAllocation;
	}
	
}
