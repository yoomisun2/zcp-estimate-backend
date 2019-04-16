package io.cloudzcp.estimate.response;

import java.util.List;

import io.cloudzcp.estimate.domain.iks.IKSVm;
import io.cloudzcp.estimate.domain.iks.IKSVmVersion;

public class IKSVmResponse extends IKSVmVersion {

	private static final long serialVersionUID = 1L;
	public List<IKSVm> vms;

	public List<IKSVm> getVms() {
		return vms;
	}

	public void setVms(List<IKSVm> vms) {
		this.vms = vms;
	}
	
}
