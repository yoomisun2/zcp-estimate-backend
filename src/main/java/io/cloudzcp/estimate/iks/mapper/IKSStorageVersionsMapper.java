package io.cloudzcp.estimate.iks.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import io.cloudzcp.estimate.iks.domain.IKSStorageVersion;
import io.cloudzcp.estimate.response.IKSStorageResponse;

@Mapper
public interface IKSStorageVersionsMapper {

	@Select("select * from iks_storage_versions order by version desc")
	public List<IKSStorageVersion> findAll();
	
	@Select("select * from iks_storage_versions where version = (select max(version) from iks_storage_versions)")
	public IKSStorageResponse findByLastVersion();

	@Select("select * from iks_storage_versions where id = #{id}")
	public IKSStorageResponse findById(@Param("id") String id);

	@Insert("insert into iks_storage_versions "
			+ "(version, object_storage_price_per_day, description, created)"
			+ "values "
			+ "(#{version}, #{objectStoragePricePerDay}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(IKSStorageVersion iksStorage);
	
}
