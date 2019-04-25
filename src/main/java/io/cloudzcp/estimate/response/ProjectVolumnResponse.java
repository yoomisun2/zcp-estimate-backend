package io.cloudzcp.estimate.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.domain.project.Volumn;

public class ProjectVolumnResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private double sumMemory;
	private double sumCpu;
	private List<Cluster> clusters = new ArrayList<Cluster>();

	public double getSumMemory() {
		return sumMemory;
	}
	public void setSumMemory(double sumMemory) {
		this.sumMemory = sumMemory;
	}
	public double getSumCpu() {
		return sumCpu;
	}
	public void setSumCpu(double sumCpu) {
		this.sumCpu = sumCpu;
	}
	public List<Cluster> getClusters() {
		return clusters;
	}
	public void setClusters(List<Cluster> clusters) {
		this.clusters = clusters;
	}
	
	public void addCluster(String name, List<Volumn> applications) {
		Cluster cluster = new Cluster();
		cluster.setName(name);
		cluster.setApplications(applications);
		cluster.setSumCpu(applications.stream().mapToDouble(Volumn::getPodCpuLimitSum).sum()/1000);
		cluster.setSumMemory(Math.ceil(applications.stream().mapToDouble(Volumn::getPodMemoryLimitSum).sum()/1024));
		
		clusters.add(cluster);
	}
	
	public List<Volumn> findAllVolumn() {
		List<Volumn> volumns = new ArrayList<Volumn>();
		clusters.forEach(cluster -> {
			cluster.getApplications().forEach(volumn -> {
				volumn.setClusterName(cluster.getName());
				volumns.add(volumn);
			});
		});
		return volumns;
	}
}

class Cluster implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private double sumMemory;
	private double sumCpu;
	private List<Volumn> applications;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSumMemory() {
		return sumMemory;
	}
	public void setSumMemory(double sumMemory) {
		this.sumMemory = sumMemory;
	}
	public double getSumCpu() {
		return sumCpu;
	}
	public void setSumCpu(double sumCpu) {
		this.sumCpu = sumCpu;
	}
	public List<Volumn> getApplications() {
		return applications;
	}
	public void setApplications(List<Volumn> applications) {
		this.applications = applications;
	}
}