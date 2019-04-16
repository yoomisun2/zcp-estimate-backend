package io.cloudzcp.estimate.domain.iks;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IKSVm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private int core;
	private int memory;
	private int nwSpeed;
	private int sharedPricePerHour;
	private int dedicatedPricePerHour;
	private int iksVmVersionVersion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCore() {
		return core;
	}

	public void setCore(int core) {
		this.core = core;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getNwSpeed() {
		return nwSpeed;
	}

	public void setNwSpeed(int nwSpeed) {
		this.nwSpeed = nwSpeed;
	}

	public int getSharedPricePerHour() {
		return sharedPricePerHour;
	}

	public void setSharedPricePerHour(int sharedPricePerHour) {
		this.sharedPricePerHour = sharedPricePerHour;
	}

	public int getDedicatedPricePerHour() {
		return dedicatedPricePerHour;
	}

	public void setDedicatedPricePerHour(int dedicatedPricePerHour) {
		this.dedicatedPricePerHour = dedicatedPricePerHour;
	}

	public int getIksVmVersionVersion() {
		return iksVmVersionVersion;
	}

	public void setIksVmVersionVersion(int iksVmVersionVersion) {
		this.iksVmVersionVersion = iksVmVersionVersion;
	}
	
}
