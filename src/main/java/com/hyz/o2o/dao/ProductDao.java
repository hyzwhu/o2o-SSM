package com.hyz.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyz.o2o.entity.Product;

public interface ProductDao {
	int insertProduct(Product ProductCondition);

	List<Product> queryProductList(@Param("productCondition") Product productCondition, @Param("rowIndex") int rowIndex,
			@Param("pageSize") int pageSize);

	int queryProductCount(@Param("productCondition") Product productCondition);

	Product queryProductById(long productId);

	int updateProduct(Product product);
	
}
