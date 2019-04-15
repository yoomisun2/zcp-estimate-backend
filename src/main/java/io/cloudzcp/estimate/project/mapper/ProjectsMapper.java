package io.cloudzcp.estimate.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.project.domain.Project;
import io.cloudzcp.estimate.response.ProjectSummaryResponse;

@Mapper
public interface ProjectsMapper {

	@Select("   select id, name, description, created, created_dt, estimate_id, estimate_version,  "
			+ "		   sum(cloudZServiceMonthlyPrice) as cloudZServiceMonthlyPrice, sum(cloudZServiceYearlyPrice) as cloudZServiceYearlyPrice, "
			+ "		   sum(storageServiceMonthlyPrice) as storageServiceMonthlyPrice, sum(storageServiceYearlyPrice) as storageServiceYearlyPrice "
			+ " from ("
			+ " 		select p.id, p.name, p.description, p.created, p.created_dt, e.id as estimate_id, e.version as estimate_version, "
			+ " 				 if(i.estimate_type = 'CloudZService', i.price_per_monthly, 0) as cloudZServiceMonthlyPrice, "
			+ " 				 if(i.estimate_type = 'CloudZService', i.price_per_yearly , 0) as cloudZServiceYearlyPrice,"
			+ " 				 if(i.estimate_type = 'StorageService', i.price_per_monthly , 0) as storageServiceMonthlyPrice,"
			+ " 				 if(i.estimate_type = 'StorageService', i.price_per_yearly , 0) as storageServiceYearlyPrice"
			+ " 		from projects p "
			+ " 		     left outer join estimates e on e.project_id = p.id and e.version = (select max(version) from estimates where project_id = e.project_id)"
			+ " 		     left outer join estimate_items i on i.estimate_id = e.id"
			+ " 	  ) as summary"
			+ "  group by id, name, description, created, created_dt, estimate_id, estimate_version")
	public List<ProjectSummaryResponse> findAll();
	
	@Select("select * from projects where id = #{id}")
	public Project findById(@Param("id") int id);
	
	@Insert("insert into projects "
			+ " (name, description, created) "
			+ " values "
			+ " (#{name}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int insert(Project project);
	
	@Update("update projects "
			+ " set name=#{name}, description=#{description} "
			+ " where id=#{id}")
	public int update(Project project);
	
	@Delete("delete from projects where id = #{id} ")
	public int delete(@Param("id") int id);

}
