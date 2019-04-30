package io.cloudzcp.estimate.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.cloudzcp.estimate.domain.project.Environment;
import io.cloudzcp.estimate.domain.project.Volume;

public class ProjectVolumeResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private double sumMemory;
	private double sumCpu;
	private List<Cluster> environments = new ArrayList<Cluster>();

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
	public List<Cluster> getEnvironments() {
		return environments;
	}
	public void setEnvironments(List<Cluster> environments) {
		this.environments = environments;
	}
	
	public void addCluster(int id, String name, List<Volume> applications) {
		Cluster cluster = new Cluster();
		cluster.setId(id);
		cluster.setName(name);
		
		if(applications != null) {
			cluster.setApplications(applications);
			cluster.setSumCpu(applications.stream().mapToDouble(Volume::getPodCpuLimitSum).sum()/1000);
			cluster.setSumMemory(Math.ceil(applications.stream().mapToDouble(Volume::getPodMemoryLimitSum).sum()/1024));
		}
		
		environments.add(cluster);
	}
	
	public List<Volume> findAllVolume() {
		List<Volume> volumes = new ArrayList<Volume>();
		environments.forEach(cluster -> {
			cluster.getApplications().forEach(volume -> {
				volume.setEnvironmentId(cluster.getId());
				volumes.add(volume);
			});
		});
		return volumes;
	}
	
	public List<Environment> findAllEnvironment() {
		return environments.stream().map(c -> (Environment) c).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public List<Volume> findVolume(Environment env) {
		return environments.get(environments.indexOf(env)).getApplications();
	}
}

class Cluster extends Environment {

	private static final long serialVersionUID = 1L;
	private double sumMemory;
	private double sumCpu;
	private List<Volume> applications = new ArrayList<Volume>();

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
	public List<Volume> getApplications() {
		return applications;
	}
	public void setApplications(List<Volume> applications) {
		this.applications = applications;
	}
}