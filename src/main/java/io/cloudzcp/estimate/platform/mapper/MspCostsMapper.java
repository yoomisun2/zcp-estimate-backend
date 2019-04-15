package io.cloudzcp.estimate.platform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.platform.domain.MspCost;

@Mapper
public interface MspCostsMapper {

	@Insert("insert into msp_costs "
			+ "(product_id, alias, memory, cost, msp_cost_version_version)"
			+ "values "
			+ "(#{productId}, #{alias}, #{memory}, #{cost}, #{mspCostVersionVersion})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(MspCost mspCost);

	@Select("select * from msp_costs where msp_cost_version_version = #{version} order by memory asc ")
	public List<MspCost> findByVersion(@Param("version") int version);
	
	@Delete("delete from msp_costs where product_id = #{productId} ")
	public int deleteByProductId(@Param("productId") int productId);
}
