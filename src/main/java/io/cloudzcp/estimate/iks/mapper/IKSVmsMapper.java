package io.cloudzcp.estimate.iks.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.iks.domain.IKSVm;

@Mapper
public interface IKSVmsMapper {

	@Insert("insert into iks_vms "
			+ "(name, core, memory, nw_speed, shared_price_per_hour, dedicated_price_per_hour, iks_vm_version_version)"
			+ "values "
			+ "(#{name}, #{core}, #{memory}, #{nwSpeed}, #{sharedPricePerHour}, #{dedicatedPricePerHour}, #{iksVmVersionVersion})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(IKSVm iksVm);

	@Select("select * from iks_vms where iks_vm_version_version = #{version}")
	public List<IKSVm> findByVersion(@Param("version") int version);
}
