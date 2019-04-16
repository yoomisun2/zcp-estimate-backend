package io.cloudzcp.estimate.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.project.Estimate;
import io.cloudzcp.estimate.response.EstimateHistoryResponse;
import io.cloudzcp.estimate.response.EstimateResponse;

@Mapper
public interface EstimatesMapper {

	@Select("   select * from ("
			+ " select e.id, e.project_id, e.version, e.label, e.description, e.general_id, e.iks_vm_version_id, e.iks_storage_version_id, e.msp_cost_version_id, e.created, e.created_dt,"
			+ " 		 sum(cloudZServiceMonthlyPrice) as cloudZServiceMonthlyPrice, sum(cloudZServiceYearlyPrice) as cloudZServiceYearlyPrice, "
			+ " 		 sum(storageServiceMonthlyPrice) as storageServiceMonthlyPrice, sum(storageServiceYearlyPrice) as storageServiceYearlyPrice"
			+ " from ("
			+ " 		select e.id, e.project_id, e.version, e.label, e.description, e.general_id, e.iks_vm_version_id, e.iks_storage_version_id, e.msp_cost_version_id, e.created, e.created_dt,"
			+ " 			   if(i.estimate_type = 'CloudZService', i.price_per_monthly, 0) as cloudZServiceMonthlyPrice, "
			+ " 			   if(i.estimate_type = 'CloudZService', i.price_per_yearly , 0) as cloudZServiceYearlyPrice,"
			+ " 			   if(i.estimate_type = 'StorageService', i.price_per_monthly , 0) as storageServiceMonthlyPrice, "
			+ " 			   if(i.estimate_type = 'StorageService', i.price_per_yearly , 0) as storageServiceYearlyPrice"
			+ " 		from estimates e "
			+ " 			  left outer join estimate_items i on i.estimate_id = e.id"
			+ " 		where e.project_id = #{projectId}"
			+ " 	  ) as e"
			+ "  group by e.id, e.project_id, e.version, e.label, e.description, e.general_id, e.iks_vm_version_id, e.iks_storage_version_id, e.msp_cost_version_id, e.created, e.created_dt"
			+ " ) as e"
			+ " order by version desc")
	public List<EstimateHistoryResponse> findByProjectId(@Param("projectId") int projectId);
	
	@Select("select * from estimates where project_id = #{projectId} and version = (select max(version) from estimates where project_id = #{projectId})")
	public EstimateResponse findByLastVersion(@Param("projectId") int projectId);
	
	@Select("select * from estimates where id = #{id} ")
	public EstimateResponse findById(@Param("id") int id);
	
	@Insert("insert into estimates "
			+ "(project_id, version, label, description, general_id, iks_vm_version_id, iks_storage_version_id, msp_cost_version_id, created)"
			+ "values "
			+ "(#{projectId}, #{version}, #{label}, #{description}, #{generalId}, #{iksVmVersionId}, #{iksStorageVersionId}, #{mspCostVersionId}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(Estimate estimate);
	
	@Delete("delete from estimates where id = #{id}")
	public int delete(@Param("id") int id);
	
	@Delete("delete from estimates where project_id = #{projectId}")
	public int deleteByProjectId(@Param("projectId") int projectId);
	
}
