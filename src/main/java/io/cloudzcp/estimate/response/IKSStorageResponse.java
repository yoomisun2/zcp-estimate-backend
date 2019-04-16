package io.cloudzcp.estimate.response;

import java.util.List;

import io.cloudzcp.estimate.domain.iks.IKSFileStorage;
import io.cloudzcp.estimate.domain.iks.IKSStorageVersion;

public class IKSStorageResponse extends IKSStorageVersion {

	private static final long serialVersionUID = 1L;

	private List<IKSFileStorage> fileStorages;

	public List<IKSFileStorage> getFileStorages() {
		return fileStorages;
	}

	public void setFileStorages(List<IKSFileStorage> fileStorages) {
		this.fileStorages = fileStorages;
	}
	
}
