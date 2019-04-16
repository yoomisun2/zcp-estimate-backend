package io.cloudzcp.estimate.mapper.project;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.domain.project.Volumn;

@Mapper
public interface VolumnsMapper {

	@Select("select * from volumns where project_id = #{projectId} order by cluster_name asc, app_name asc")
	public List<Volumn> findByProjectId(@Param("projectId") int projectId);
	
	@Insert("insert into volumns "
			+ " (cluster_name, app_name, app_memory_min, app_memory_max, replica_count, pod_momory_request, pod_memory_limit, pod_cpu_request, pod_cpu_limit, "
			+ "  pod_momory_request_sum, pod_memory_limit_sum, pod_cpu_request_sum, pod_cpu_limit_sum, project_id, created) "
			+ " values "
			+ " (#{clusterName}, #{appName}, #{appMemoryMin}, #{appMemoryMax}, #{replicaCount}, #{podMemoryRequest}, #{podMemoryLimit}, #{podCpuRequest}, #{podCpuLimit},"
			+ "  #{podMemoryRequestSum}, #{podMemoryLimitSum}, #{podCpuRequestSum}, #{podCpuLimitSum}, #{projectId}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Volumn application);
	
	@Update("update volumns "
			+ " set app_name = #{appName}, "
			+ "     app_memory_min = #{appMemoryMin}, "
			+ "     app_memory_max = #{appMemoryMax}, "
			+ "     replica_count = #{replicaCount}, "
			+ "     pod_momory_request = #{podMemoryRequest}, "
			+ "     pod_memory_limit = #{podMemoryLimit}, "
			+ "     pod_cpu_request = #{podCpuRequest}, "
			+ "     pod_cpu_limit = #{podCpuLimit}, "
			+ "     pod_momory_request_sum = #{podMemoryRequestSum}, "
			+ "     pod_memory_limit_sum = #{podMemoryLimitSum}, "
			+ "     pod_cpu_request_sum = #{podCpuRequestSum}, "
			+ "     pod_cpu_limit_sum = #{podCpuLimitSum} "
			+ " where id = #{id} "
			)
	public int update(Volumn application);
	
	@Delete("delete from volumns where id = #{id} ")
	public int delete(@Param("id") int id);

	@Delete("delete from volumns where project_id = #{projectId} ")
	public int deleteByProjectId(@Param("projectId") int projectId);

}
