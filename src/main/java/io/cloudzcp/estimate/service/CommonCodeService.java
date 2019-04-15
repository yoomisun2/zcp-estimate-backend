package io.cloudzcp.estimate.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import io.cloudzcp.estimate.constant.CommonConstants;
import io.cloudzcp.estimate.exception.NoDataFoundException;

@Service
public class CommonCodeService {

	private static Map<String, List<Object>> codeMaps = null;
	
	@PostConstruct
	public void initialize() {
		codeMaps = new HashMap<String, List<Object>>();

		codeMaps.put("hardware_type", Arrays.asList(CommonConstants.HARDWARE_TYPE_SHARED, CommonConstants.HARDWARE_TYPE_DEDICATED));
		codeMaps.put("file_storage_type", Arrays.asList(CommonConstants.FILE_STORAGE_TYPE_ENDURANCE)); //, CommonConstants.FILE_STORAGE_TYPE_PERFORMANCE));
		codeMaps.put("storage_type", Arrays.asList(CommonConstants.STORAGE_TYPE_BLOCK, CommonConstants.STORAGE_TYPE_FILE, CommonConstants.STORAGE_TYPE_LOCAL, CommonConstants.STORAGE_TYPE_OBJECT));
		codeMaps.put("endurance_iops", Arrays.asList(CommonConstants.ENDURANCE_IOPS_1, CommonConstants.ENDURANCE_IOPS_2, CommonConstants.ENDURANCE_IOPS_3, CommonConstants.ENDURANCE_IOPS_4));
		codeMaps.put("discount_rate", Arrays.asList(CommonConstants.DISCOUNT_RATE_10, CommonConstants.DISCOUNT_RATE_20, CommonConstants.DISCOUNT_RATE_25, CommonConstants.DISCOUNT_RATE_30, CommonConstants.DISCOUNT_RATE_40));
		codeMaps.put("backup_yn", Arrays.asList(CommonConstants.BACKUP_YN_Y, CommonConstants.BACKUP_YN_N));
		codeMaps.put("classification_type", Arrays.asList(CommonConstants.CLASSIFICATION_TYPE_VM, CommonConstants.CLASSIFICATION_TYPE_FILE_STORAGE, CommonConstants.CLASSIFICATION_TYPE_OBJECT_STORAGE, CommonConstants.CLASSIFICATION_TYPE_IP_ALLOCATION, CommonConstants.CLASSIFICATION_TYPE_LABOR_COST));
	}
	
	public List<Object> getCommonCode(String groupName) {
		List<Object> codes = codeMaps.get(groupName);
		if(codes == null) {
			throw new NoDataFoundException(String.format("%s not found", groupName));
		}
		
		return codes;
	}
	
}
