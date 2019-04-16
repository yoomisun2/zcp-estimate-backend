package io.cloudzcp.estimate.mapper.platform;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.platform.MspCostVersion;
import io.cloudzcp.estimate.response.ProductMspCostVersion;

@Mapper
public interface MspCostVersionsMapper {

	@Select("select * from msp_cost_versions order by version desc")
	public List<MspCostVersion> findAll();
	
	@Select("select * from msp_cost_versions where version = (select max(version) from msp_cost_versions)")
	public ProductMspCostVersion findByLastVersion();
	
	@Select("select * from msp_cost_versions where id = #{id}")
	public ProductMspCostVersion findById(@Param("id") int id);
	
	@Insert("insert into msp_cost_versions "
			+ "(version, description, created)"
			+ "values "
			+ "(#{version}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(MspCostVersion mspCostVersion);
}
