package io.cloudzcp.estimate.domain.project;

import java.io.Serializable;

public class Estimate implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private int projectId;
	private int version;
	private String label;
	private String description;
	private int generalId;
	private int iksVmVersionId;
	private int iksStorageVersionId;
	private int mspCostVersionId;
	private String created;
	private String createdDt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getGeneralId() {
		return generalId;
	}
	public void setGeneralId(int generalId) {
		this.generalId = generalId;
	}
	public int getIksVmVersionId() {
		return iksVmVersionId;
	}
	public void setIksVmVersionId(int iksVmVersionId) {
		this.iksVmVersionId = iksVmVersionId;
	}
	public int getIksStorageVersionId() {
		return iksStorageVersionId;
	}
	public void setIksStorageVersionId(int iksStorageVersionId) {
		this.iksStorageVersionId = iksStorageVersionId;
	}
	public int getMspCostVersionId() {
		return mspCostVersionId;
	}
	public void setMspCostVersionId(int mspCostVersionId) {
		this.mspCostVersionId = mspCostVersionId;
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
