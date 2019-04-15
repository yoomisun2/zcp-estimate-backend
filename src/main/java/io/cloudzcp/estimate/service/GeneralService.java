package io.cloudzcp.estimate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cloudzcp.estimate.exception.NoDataFoundException;
import io.cloudzcp.estimate.iks.domain.General;
import io.cloudzcp.estimate.iks.mapper.GeneralsMapper;

@Service
public class GeneralService {

	@Autowired
	private GeneralsMapper generalsMapper;
	
	public List<General> getGeneralVersionList() {
		return generalsMapper.findAll();
	}
	
	public General getLatestGeneral() {
		return generalsMapper.findByLastVersion();
	}
	
	public General getGeneral(String id) {
		General general = generalsMapper.findById(id);
		if(general == null) {
			throw new NoDataFoundException(String.format("%s not found.", id));
		}
		
		return general;
	}
	
	@Transactional
	public void modifyGeneral(General general) {
		General lastVersion = generalsMapper.findByLastVersion();
		general.setVersion(lastVersion == null ? 1 : lastVersion.getVersion() + 1);
		generalsMapper.add(general);
	}
	
	
}
