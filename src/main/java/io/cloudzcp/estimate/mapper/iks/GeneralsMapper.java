package io.cloudzcp.estimate.mapper.iks;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.iks.General;

@Mapper
public interface GeneralsMapper {

	@Select("select * from generals where version = (select max(version) from generals)")
	public General findByLastVersion();
	
	@Insert("insert into generals "
			+ "(ibm_dc_rate, platform_cpu_per_worker, platform_memory_per_worker, exchange_rate, ip_allocation, version, description, created)"
			+ "values "
			+ "(#{ibmDcRate}, #{platformCpuPerWorker}, #{platformMemoryPerWorker}, #{exchangeRate}, #{ipAllocation}, #{version}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(General general);

	@Select("select * from generals order by version desc")
	public List<General> findAll();
	
	@Select("select * from generals where id = #{id}")
	public General findById(@Param("id") String id);
	
}
