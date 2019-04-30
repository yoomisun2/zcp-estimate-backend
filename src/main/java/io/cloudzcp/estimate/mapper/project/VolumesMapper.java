package io.cloudzcp.estimate.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.domain.project.Volume;

@Mapper
public interface VolumesMapper {

//	@Select("select * from volumes where project_id = #{projectId} order by cluster_name asc, app_name asc")
	@Select("select v.*, e.name as environment_name "
			+ " from volumes v "
			+ " left join environments e on e.id = v.environment_id "
			+ " left join projects p on p.id = e.project_id "
			+ " where p.id = #{projectId} "
			+ " order by e.name, v.app_name")
	public List<Volume> findByProjectId(@Param("projectId") int projectId);
	
	@Insert("insert into volumes "
			+ " (app_name, app_memory_min, app_memory_max, replica_count, pod_memory_request, pod_memory_limit, pod_cpu_request, pod_cpu_limit, "
			+ "  pod_memory_request_sum, pod_memory_limit_sum, pod_cpu_request_sum, pod_cpu_limit_sum, environment_id, created) "
			+ " values "
			+ " (#{appName}, #{appMemoryMin}, #{appMemoryMax}, #{replicaCount}, #{podMemoryRequest}, #{podMemoryLimit}, #{podCpuRequest}, #{podCpuLimit},"
			+ "  #{podMemoryRequestSum}, #{podMemoryLimitSum}, #{podCpuRequestSum}, #{podCpuLimitSum}, #{environmentId}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Volume application);
	
	@Update("update volumes "
			+ " set app_name = #{appName}, "
			+ "     app_memory_min = #{appMemoryMin}, "
			+ "     app_memory_max = #{appMemoryMax}, "
			+ "     replica_count = #{replicaCount}, "
			+ "     pod_memory_request = #{podMemoryRequest}, "
			+ "     pod_memory_limit = #{podMemoryLimit}, "
			+ "     pod_cpu_request = #{podCpuRequest}, "
			+ "     pod_cpu_limit = #{podCpuLimit}, "
			+ "     pod_memory_request_sum = #{podMemoryRequestSum}, "
			+ "     pod_memory_limit_sum = #{podMemoryLimitSum}, "
			+ "     pod_cpu_request_sum = #{podCpuRequestSum}, "
			+ "     pod_cpu_limit_sum = #{podCpuLimitSum} "
			+ " where id = #{id} "
			)
	public int update(Volume application);
	
	@Delete("delete from volumes where id = #{id} ")
	public int delete(@Param("id") int id);

	@Delete("delete from volumes where environment_id #{environmentId}) ")
	public int deleteByEnvironmentId(@Param("environmentId") int environmentId);

	@Delete("delete from volumes where environment_id in (select id from environments where project_id = #{projectId}) ")
	public int deleteByProjectId(@Param("projectId") int projectId);

}
