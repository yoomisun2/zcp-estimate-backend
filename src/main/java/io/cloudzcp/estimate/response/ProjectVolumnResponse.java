package io.cloudzcp.estimate.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.project.domain.Volumn;

public class ProjectVolumnResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private float memorySum;
	private float cpuSum;
	
	private List<Cluster> clusters = new ArrayList<Cluster>();

	public float getMemorySum() {
		return memorySum;
	}
	public void setMemorySum(float memorySum) {
		this.memorySum = memorySum;
	}
	public float getCpuSum() {
		return cpuSum;
	}
	public void setCpuSum(float cpuSum) {
		this.cpuSum = cpuSum;
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
	private List<Volumn> applications;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Volumn> getApplications() {
		return applications;
	}
	public void setApplications(List<Volumn> applications) {
		this.applications = applications;
	}
}