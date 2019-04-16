package io.cloudzcp.estimate.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.domain.platform.Addon;

public class AddonResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String serviceName;
	private int sumCpu;
	private int sumMemory;
	private int sumDisk;
	
	private List<Addon> applications;
	
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getSumCpu() {
		return sumCpu;
	}
	public void setSumCpu(int sumCpu) {
		this.sumCpu = sumCpu;
	}
	public int getSumMemory() {
		return sumMemory;
	}
	public void setSumMemory(int sumMemory) {
		this.sumMemory = sumMemory;
	}
	public int getSumDisk() {
		return sumDisk;
	}
	public void setSumDisk(int sumDisk) {
		this.sumDisk = sumDisk;
	}
	public List<Addon> getApplications() {
		return applications == null ? new ArrayList<Addon>() : applications;
	}
	public void setApplications(List<Addon> applications) {
		this.applications = applications;
	}
	
}
