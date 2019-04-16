package io.cloudzcp.estimate.mapper.platform;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.cloudzcp.estimate.domain.platform.Product;
import io.cloudzcp.estimate.response.ProductMspCost;

@Mapper
public interface ProductsMapper {

	@Select("select *, (select count(distinct(service_name)) from addons  where product_id=p.id) as serviceCount from products as p order by name asc")
	public List<ProductMspCost> findAll();
	
	@Select("select * from products where id=#{id}")
	public Product findById(@Param("id") int id);
	
	@Insert("insert into products"
			+ " (name, description, created) "
			+ " values "
			+ " (#{name}, #{description}, #{created})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(Product productService);
	
	@Update("update products"
			+ " set name=#{name}, description=#{description} "
			+ " where id=#{id} ")
	public int update(Product productService);
	
	@Delete("delete from products where id = #{id}")
	public int delete(@Param("id") int id);
	
}
