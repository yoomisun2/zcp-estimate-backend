package io.cloudzcp.estimate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.domain.iks.IKSStorageVersion;
import io.cloudzcp.estimate.domain.iks.IKSVmVersion;
import io.cloudzcp.estimate.exception.EntityNotFoundException;
import io.cloudzcp.estimate.mapper.iks.IKSFileStoragesMapper;
import io.cloudzcp.estimate.mapper.iks.IKSStorageVersionsMapper;
import io.cloudzcp.estimate.mapper.iks.IKSVmVersionsMapper;
import io.cloudzcp.estimate.mapper.iks.IKSVmsMapper;
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
			iksVm.setVms(iksVmsMapper.findByVersionId(iksVm.getId()));
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
				iksVm.setIksVmVersionId(iksVmVersion.getId());
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
			throw new EntityNotFoundException(String.format("VM is not found : %s", id));
		} else {
			iksVmVersion.setVms(iksVmsMapper.findByVersionId(iksVmVersion.getId()));
		}
		
		return iksVmVersion;
	}
	
	public IKSStorageResponse getLatestStorage() {
		IKSStorageResponse iksStorage = iksStorageVersionsMapper.findByLastVersion();
		if(iksStorage != null) {
			iksStorage.setFileStorages(iksFileStoragesMapper.findByVersionId(iksStorage.getId()));
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
				iksFileStorage.setIksStorageVersionId(iksStorageVersion.getId());
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
			throw new EntityNotFoundException(String.format("Storage is not found : %s", id));
		} else {
			iksStorageVersion.setFileStorages(iksFileStoragesMapper.findByVersionId(iksStorageVersion.getId()));
		}
		
		return iksStorageVersion;
	}
	
}
