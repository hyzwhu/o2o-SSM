package com.hyz.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hyz.o2o.entity.ProductCategory;

public interface ProductCategoryDao {

	List<ProductCategory> queryProductCategoryList(long shopId);

	/**
	 *  批量新增商品类别
	 * 
	 * @param productCategoryList
	 * @return
	 */
	int batchInsertProductCategory(List<ProductCategory> productCategoryList);

	int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
