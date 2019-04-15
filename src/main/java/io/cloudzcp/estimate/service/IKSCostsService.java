package io.cloudzcp.estimate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.exception.NoDataFoundException;
import io.cloudzcp.estimate.iks.domain.IKSStorageVersion;
import io.cloudzcp.estimate.iks.domain.IKSVmVersion;
import io.cloudzcp.estimate.iks.mapper.IKSFileStoragesMapper;
import io.cloudzcp.estimate.iks.mapper.IKSStorageVersionsMapper;
import io.cloudzcp.estimate.iks.mapper.IKSVmVersionsMapper;
import io.cloudzcp.estimate.iks.mapper.IKSVmsMapper;
import io.cloudzcp.estimate.response.IKSStorageResponse;
import io.cloudzcp.estimate.response.IKSVmResponse;

@Service
public class IKSCostsService {

	@Autowired
	private IKSVmVersionsMapper iksVmVersionsMapper;
	
	@Autowired
	private IKSVmsMapper iksVmsMapper;
	
	@Autowired
	private IKSStorageVersionsMapper iksStorageVersionsMapper;
	
	@Autowired
	private IKSFileStoragesMapper iksFileStoragesMapper;
	
	
	public IKSVmResponse getLatestVm() {
		IKSVmResponse iksVm = iksVmVersionsMapper.findByLastVersion();
		if(iksVm != null) {
			iksVm.setVms(iksVmsMapper.findByVersion(iksVm.getVersion()));
		}
		
		return iksVm;
	}
	
	@Transactional
	public void modifyVm(IKSVmResponse iksVmVersion) {
		IKSVmVersion lastVersion = iksVmVersionsMapper.findByLastVersion();
		iksVmVersion.setVersion(lastVersion == null ? 1 : lastVersion.getVersion() + 1);
		iksVmVersionsMapper.add(iksVmVersion);
		
		if(iksVmVersion.getVms() != null) {
			iksVmVersion.getVms().forEach(iksVm -> {
				iksVm.setIksVmVersionVersion(iksVmVersion.getVersion());
				iksVmsMapper.add(iksVm);
			});
		}
	}
	
	public List<IKSVmVersion> getVmVersionList() {
		return iksVmVersionsMapper.findAll();
	}
	
	public IKSVmResponse getVmVersion(int id) {
		IKSVmResponse iksVmVersion = iksVmVersionsMapper.findById(id);
		if(iksVmVersion == null) {
			throw new NoDataFoundException(String.format("%s not found.", id));
		} else {
			iksVmVersion.setVms(iksVmsMapper.findByVersion(iksVmVersion.getVersion()));
		}
		
		return iksVmVersion;
	}
	
	public IKSStorageResponse getLatestStorage() {
		IKSStorageResponse iksStorage = iksStorageVersionsMapper.findByLastVersion();
		if(iksStorage != null) {
			iksStorage.setFileStorages(iksFileStoragesMapper.findByVersion(iksStorage.getVersion()));
		}
		
		return iksStorage;
	}
	
	@Transactional
	public void modifyStorage(IKSStorageResponse iksStorageVersion) {
		IKSStorageVersion lastVersion = iksStorageVersionsMapper.findByLastVersion();
		iksStorageVersion.setVersion(lastVersion == null ? 1 : lastVersion.getVersion() + 1);
		iksStorageVersionsMapper.add(iksStorageVersion);
		
		if(iksStorageVersion.getFileStorages() != null) {
			iksStorageVersion.getFileStorages().forEach(iksFileStorage -> {
				iksFileStorage.setIksStorageVersionVersion(iksStorageVersion.getVersion());
				iksFileStoragesMapper.add(iksFileStorage);
			});
		}
	}
	
	public List<IKSStorageVersion> getStorageVersionList() {
		return iksStorageVersionsMapper.findAll();
	}
	
	public IKSStorageResponse getStorage(String id) {
		IKSStorageResponse iksStorageVersion = iksStorageVersionsMapper.findById(id);
		if(iksStorageVersion == null) {
			throw new NoDataFoundException(String.format("%s not found.", id));
		} else {
			iksStorageVersion.setFileStorages(iksFileStoragesMapper.findByVersion(iksStorageVersion.getVersion()));
		}
		
		return iksStorageVersion;
	}
	
}
