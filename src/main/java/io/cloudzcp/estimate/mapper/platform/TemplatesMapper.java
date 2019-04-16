package io.cloudzcp.estimate.mapper.platform;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.domain.platform.Template;

@Mapper
public interface TemplatesMapper {

	@Select("select * from templates where product_id = #{productId} order by id asc")
	public List<Template> findByProductId(@Param("productId") int productId);
	
	@Insert("insert into templates"
			+ "(estimate_type, service_name, classification_name, classification_type, product_id, created) "
			+ " values "
			+ " (#{estimateType}, #{serviceName}, #{classificationName}, #{classificationType}, #{productId}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Template template);
	
	@Update("update templates set "
			+ " classification_name = #{classificationName}, "
			+ " classification_type = #{classificationType} "
			+ " where id = #{id}")
	public int update(Template template);
	
	@Delete("delete from templates where id= #{id}")
	public int delete(@Param("id") int id);
	
	@Delete("delete from templates where product_id= #{productId}")
	public int deleteByProductId(@Param("productId") int productId);
}
