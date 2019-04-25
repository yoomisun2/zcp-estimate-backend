package io.cloudzcp.estimate.mapper.iks;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.domain.iks.IKSVm;

@Mapper
public interface IKSVmsMapper {

	@Insert("insert into iks_vms "
			+ "(name, core, memory, nw_speed, shared_price_per_hour, dedicated_price_per_hour, iks_vm_version_id)"
			+ "values "
			+ "(#{name}, #{core}, #{memory}, #{nwSpeed}, #{sharedPricePerHour}, #{dedicatedPricePerHour}, #{iksVmVersionId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(IKSVm iksVm);

	@Select("select * from iks_vms where iks_vm_version_id = #{versionId}")
	public List<IKSVm> findByVersionId(@Param("versionId") int versionId);
}
