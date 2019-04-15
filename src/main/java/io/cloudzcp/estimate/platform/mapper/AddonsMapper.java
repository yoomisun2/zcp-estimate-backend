package io.cloudzcp.estimate.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.platform.domain.Addon;
import io.cloudzcp.estimate.response.AddonResponse;

@Mapper
public interface AddonsMapper {

	@Select("select * from addons where product_id = #{productId}")
	public List<Addon> findByProductId(@Param("productId") int productId);
	
	@Select("select service_name, sum(cpu) as sumCpu, sum(memory) as sumMemory, sum(disk) as sumDisk "
			+ " from addons "
			+ " where product_id = #{productId}"
			+ " group by service_name order by id asc")
	public List<AddonResponse> findServiceSummaryByProductId(@Param("productId") int productId);
	
	@Insert("insert into addons "
			+ " (service_name, application_name, cpu, memory, disk, storage_type, backup_yn, description, product_id, created) "
			+ " values "
			+ " (#{serviceName}, #{applicationName}, #{cpu}, #{memory}, #{disk}, #{storageType}, #{backupYn}, #{description}, #{productId}, #{created}) ")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Addon application);
	
	@Update("update addons "
			+ " set application_name = #{applicationName}, "
			+ "     cpu = #{cpu}, "
			+ "     memory = #{memory}, "
			+ "     disk = #{disk}, "
			+ "     storage_type = #{storageType}, "
			+ "     backup_yn = #{backupYn}, "
			+ "     description = #{description} "
			+ " where id = #{id} ")
	public int update(Addon application);
	
	@Delete("delete from addons where id = #{id}")
	public int delete(@Param("id") int id);
	
	@Delete("delete from addons where product_id = #{productId} ")
	public int deleteByProductId(@Param("productId") int productId);
}
