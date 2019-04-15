package io.cloudzcp.estimate.iks.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.iks.domain.IKSVmVersion;
import io.cloudzcp.estimate.response.IKSVmResponse;

@Mapper
public interface IKSVmVersionsMapper {

	@Select("select * from iks_vm_versions order by version desc")
	public List<IKSVmVersion> findAll();
	
	@Select("select * from iks_vm_versions where version = (select max(version) from iks_vm_versions)")
	public IKSVmResponse findByLastVersion();

	@Select("select * from iks_vm_versions where id = #{id}")
	public IKSVmResponse findById(@Param("id") int id);

	@Insert("insert into iks_vm_versions "
			+ "(version, description, created)"
			+ "values "
			+ "(#{version}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(IKSVmVersion iksVmVersion);
	
}
