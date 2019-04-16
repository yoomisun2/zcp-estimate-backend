package io.cloudzcp.estimate.domain.iks;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class IKSFileStorage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int disk;
	private float iops1PricePerHour;
	private float iops2PricePerHour;
	private float iops3PricePerHour;
	private float iops4PricePerHour;
	private int iksStorageVersionVersion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDisk() {
		return disk;
	}
	public void setDisk(int disk) {
		this.disk = disk;
	}
	public float getIops1PricePerHour() {
		return iops1PricePerHour;
	}
	public void setIops1PricePerHour(float iops1PricePerHour) {
		this.iops1PricePerHour = iops1PricePerHour;
	}
	public int getIksStorageVersionVersion() {
		return iksStorageVersionVersion;
	}
	public void setIksStorageVersionVersion(int iksStorageVersionVersion) {
		this.iksStorageVersionVersion = iksStorageVersionVersion;
	}
	public float getIops2PricePerHour() {
		return iops2PricePerHour;
	}
	public void setIops2PricePerHour(float iops2PricePerHour) {
		this.iops2PricePerHour = iops2PricePerHour;
	}
	public float getIops3PricePerHour() {
		return iops3PricePerHour;
	}
	public void setIops3PricePerHour(float iops3PricePerHour) {
		this.iops3PricePerHour = iops3PricePerHour;
	}
	public float getIops4PricePerHour() {
		return iops4PricePerHour;
	}
	public void setIops4PricePerHour(float iops4PricePerHour) {
		this.iops4PricePerHour = iops4PricePerHour;
	}
	
}
