package io.cloudzcp.estimate.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.domain.project.Environment;

@Mapper
public interface EnvironmentsMapper {

	@Select("select * from environments where id = #{id}")
	public Environment findById(@Param("id") int id);
	
	@Select("select * from environments where project_id = #{projectId}")
	public List<Environment> findByProjectId(@Param("projectId") int projectId);
	
	@Insert("insert into environments "
			+ " (name, project_id, created) "
			+ " values "
			+ " (#{name}, #{projectId}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Environment environment);
	
	@Update("update environments "
			+ " set name=#{name} "
			+ " where id=#{id}")
	public int update(Environment environment);
	
	@Delete("delete from environments where id = #{id} ")
	public int delete(@Param("id") int id);
	
	@Delete("delete from environments where project_id = #{projectId} ")
	public int deleteByProjectId(@Param("projectId") int projectId);
}
