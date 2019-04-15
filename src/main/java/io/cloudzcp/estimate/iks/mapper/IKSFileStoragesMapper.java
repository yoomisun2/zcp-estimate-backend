package io.cloudzcp.estimate.iks.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.iks.domain.IKSFileStorage;

@Mapper
public interface IKSFileStoragesMapper {

	@Insert("insert into iks_file_storages "
			+ "(disk, iops1_price_per_hour, iops2_price_per_hour, iops3_price_per_hour, iops4_price_per_hour, iks_storage_version_version)"
			+ "values "
			+ "(#{disk}, #{iops1PricePerHour}, #{iops2PricePerHour}, #{iops3PricePerHour}, #{iops4PricePerHour}, #{iksStorageVersionVersion})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(IKSFileStorage iksFileStorage);

	@Select("select * from iks_file_storages where iks_storage_version_version = #{version}")
	public List<IKSFileStorage> findByVersion(@Param("version") int version);
}
