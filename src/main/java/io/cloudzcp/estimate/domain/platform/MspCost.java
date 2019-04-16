package io.cloudzcp.estimate.domain.platform;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MspCost implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private int productId;
	private String alias;
	private int memory;
	private int cost;
	
	@JsonIgnore
	private int mspCostVersionVersion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getMspCostVersionVersion() {
		return mspCostVersionVersion;
	}

	public void setMspCostVersionVersion(int mspCostVersionVersion) {
		this.mspCostVersionVersion = mspCostVersionVersion;
	}
	
}
