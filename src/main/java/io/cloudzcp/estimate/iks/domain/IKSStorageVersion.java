package io.cloudzcp.estimate.iks.domain;

import java.io.Serializable;

public class IKSStorageVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int version;
	private String description;
	private int objectStoragePricePerDay;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getObjectStoragePricePerDay() {
		return objectStoragePricePerDay;
	}

	public void setObjectStoragePricePerDay(int objectStoragePricePerDay) {
		this.objectStoragePricePerDay = objectStoragePricePerDay;
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
