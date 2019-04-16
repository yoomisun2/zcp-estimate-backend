package io.cloudzcp.estimate.domain.project;

import java.io.Serializable;

public class Volumn implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String clusterName;
	private String appName;
	private int appMemoryMin;
	private int appMemoryMax;
	private int replicaCount;
	private int podMemoryRequest;
	private int podMemoryLimit;
	private int podCpuRequest;
	private int podCpuLimit;
	private float podMemoryRequestSum;
	private float podMemoryLimitSum;
	private float podCpuRequestSum;
	private float podCpuLimitSum;
	private int projectId;
	private String created;
	private String createdDt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public int getAppMemoryMin() {
		return appMemoryMin;
	}
	public void setAppMemoryMin(int appMemoryMin) {
		this.appMemoryMin = appMemoryMin;
	}
	public int getAppMemoryMax() {
		return appMemoryMax;
	}
	public void setAppMemoryMax(int appMemoryMax) {
		this.appMemoryMax = appMemoryMax;
	}
	public int getReplicaCount() {
		return replicaCount;
	}
	public void setReplicaCount(int replicaCount) {
		this.replicaCount = replicaCount;
	}
	public int getPodMemoryRequest() {
		return podMemoryRequest;
	}
	public void setPodMemoryRequest(int podMemoryRequest) {
		this.podMemoryRequest = podMemoryRequest;
	}
	public int getPodMemoryLimit() {
		return podMemoryLimit;
	}
	public void setPodMemoryLimit(int podMemoryLimit) {
		this.podMemoryLimit = podMemoryLimit;
	}
	public int getPodCpuRequest() {
		return podCpuRequest;
	}
	public void setPodCpuRequest(int podCpuRequest) {
		this.podCpuRequest = podCpuRequest;
	}
	public int getPodCpuLimit() {
		return podCpuLimit;
	}
	public void setPodCpuLimit(int podCpuLimit) {
		this.podCpuLimit = podCpuLimit;
	}
	public float getPodMemoryRequestSum() {
		return podMemoryRequestSum;
	}
	public void setPodMemoryRequestSum(float podMemoryRequestSum) {
		this.podMemoryRequestSum = podMemoryRequestSum;
	}
	public float getPodMemoryLimitSum() {
		return podMemoryLimitSum;
	}
	public void setPodMemoryLimitSum(float podMemoryLimitSum) {
		this.podMemoryLimitSum = podMemoryLimitSum;
	}
	public float getPodCpuRequestSum() {
		return podCpuRequestSum;
	}
	public void setPodCpuRequestSum(float podCpuRequestSum) {
		this.podCpuRequestSum = podCpuRequestSum;
	}
	public float getPodCpuLimitSum() {
		return podCpuLimitSum;
	}
	public void setPodCpuLimitSum(float podCpuLimitSum) {
		this.podCpuLimitSum = podCpuLimitSum;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
	

}
