package io.cloudzcp.estimate.response;

import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.platform.domain.MspCost;
import io.cloudzcp.estimate.platform.domain.Product;

public class ProductMspCost extends Product {
	
	private static final long serialVersionUID = 1L;
	private List<MspCost> mspCosts = new ArrayList<MspCost>();
	private int serviceCount;
	
	public List<MspCost> getMspCosts() {
		return mspCosts;
	}
	
	public void setMspCosts(List<MspCost> mspCosts) {
		this.mspCosts = mspCosts;
	}

	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}
	
}