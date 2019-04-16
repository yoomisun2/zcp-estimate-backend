package io.cloudzcp.estimate.response;

import java.util.ArrayList;
import java.util.List;

import io.cloudzcp.estimate.domain.platform.MspCostVersion;

public class ProductMspCostVersion extends MspCostVersion {

	private static final long serialVersionUID = 1L;

	private List<ProductMspCost> products = new ArrayList<ProductMspCost>();
	
	public List<ProductMspCost> getProducts() {
		return products;
	}

	public void setProducts(List<ProductMspCost> products) {
		this.products = products;
	}
	
}
