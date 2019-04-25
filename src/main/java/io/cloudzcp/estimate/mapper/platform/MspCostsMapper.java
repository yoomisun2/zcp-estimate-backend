package io.cloudzcp.estimate.mapper.platform;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.platform.MspCost;

@Mapper
public interface MspCostsMapper {

	@Insert("insert into msp_costs "
			+ "(product_id, alias, memory, cost, msp_cost_version_id)"
			+ "values "
			+ "(#{productId}, #{alias}, #{memory}, #{cost}, #{mspCostVersionId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(MspCost mspCost);

	@Select("select * from msp_costs where msp_cost_version_id = #{versionId} order by memory asc ")
	public List<MspCost> findByVersionId(@Param("versionId") int versionId);
	
	@Delete("delete from msp_costs where product_id = #{productId} ")
	public int deleteByProductId(@Param("productId") int productId);
}
