package io.cloudzcp.estimate.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.project.EstimateItem;
import io.cloudzcp.estimate.response.EstimateSummary;

@Mapper
public interface EstimateItemsMapper {

	@Select("   select items.id, items.estimate_id, items.estimate_type, items.environment_id, "
			+ "        ifnull(e.name, items.environment_name) as environment_name, items.product_id, items.service_name, "
			+ " 	   items.classification_name, items.classification_type, items.addon_id,  "
			+ "		   ifnull(a.application_name, ifnull(items.addon_application_name, '')) as addon_application_name,  "
			+ " 	   items.iks_vm_id , v.name as iks_vm_name, "
			+ "		   items.hardware_type, items.storage_type, items.endurance_iops, "
			+ "		   items.iks_file_storage_id, f.disk as iks_file_storage_disk,  "
			+ " 	   items.number, items.cores, items.memory, items.price_per_monthly, items.price_per_yearly, items.sort, items.created, items.created_dt,  "
			+ " 	   p.name as product_name  "
			+ " from estimate_items as items "
			+ " 	 left outer join environments e on e.id = items.environment_id "
			+ " 	 left outer join products p on p.id = items.product_id "
			+ " 	 left outer join iks_vms v on v.id = items.iks_vm_id "
			+ " 	 left outer join iks_file_storages f on f.id = items.iks_file_storage_id "
			+ " 	 left outer join addons a on a.id = items.addon_id  "
			+ " where items.estimate_id = #{estimateId} "
			+ " order by items.sort asc")
	public List<EstimateItem> findByEstimateId(@Param("estimateId") int estimateId);
	
	@Select("   select t.environment_id, environment_name, product_id, product_name, estimate_type, product, cloudCost, laborCost, totalCost, "
			+ "      if(estimate_type = 'CloudZService', ceil(cloudCost/ceil(sum(v.pod_memory_limit_sum)/1024)), 0) pricePerGb "
			+ " from ( "
			+ " 	select environment_id, environment_name, product_id, product_name, estimate_type,"
			+ "        	   if(estimate_type = 'CloudZService', product_name, concat(product_name,' Storage')) as product,"
			+ " 	   	   sum(cloudCost) as cloudCost, sum(laborCost) as laborCost, sum(cloudCost) + sum(laborCost) totalCost"
			+ " 	from ("
			+ "       		select items.environment_id, ifnull(e.name, items.environment_name) as environment_name, "
			+ "              	   estimate_type, product_id, ifnull(p.name, product_id) as product_name, classification_type,"
			+ "              	   if(classification_type != 'Labor_Cost', price_per_monthly, 0) cloudCost,"
			+ "              	   if(classification_type = 'Labor_Cost', price_per_monthly, 0) laborCost,"
			+ "              	   price_per_monthly"
			+ "       		from estimate_items items"
			+ " 	       		 left outer join environments e on e.id = items.environment_id "
			+ "            		 left outer join products p on p.id = items.product_id"
			+ "       		where estimate_id = #{estimateId} "
			+ " 	) as t"
			+ " 	group by environment_id, environment_name, product_id, product_name, estimate_type "
			+ " ) as t "
			+ "		 left outer join volumes v on v.environment_id = t. environment_id "
			+ " group by t.environment_id, environment_name, product_id, product_name, estimate_type, product, cloudCost, laborCost, totalCost"
			)
	public List<EstimateSummary> getSummary(@Param("estimateId") int estimateId);

	@Insert("insert into estimate_items "
			+ "(estimate_id, estimate_type, environment_id, environment_name, product_id, service_name, classification_name, classification_type, addon_id, addon_application_name, iks_vm_id, hardware_type, storage_type, endurance_iops, iks_file_storage_id, number, cores, memory, price_per_monthly, price_per_yearly, sort, created)"
			+ "values "
			+ "(#{estimateId}, #{estimateType}, #{environmentId}, #{environmentName}, #{productId}, #{serviceName}, #{classificationName}, #{classificationType}, #{addonId}, #{addonApplicationName}, #{iksVmId}, #{hardwareType}, #{storageType}, #{enduranceIops}, #{iksFileStorageId}, #{number}, #{cores}, #{memory}, #{pricePerMonthly}, #{pricePerYearly}, #{sort}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(EstimateItem item);

	@Delete("delete from estimate_items where estimate_id = #{estimateId}")
	public int deleteByEstimateId(@Param("estimateId") int estimateId);

	@Delete("delete from estimate_items where estimate_id in (select id from estimates where project_id = #{projectId}) ")
	public int deleteByProjectId(@Param("projectId") int projectId);

}
