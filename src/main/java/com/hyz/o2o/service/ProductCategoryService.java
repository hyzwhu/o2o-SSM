package com.hyz.o2o.service;

import java.util.List;

import com.hyz.o2o.dto.ProductCategoryExecution;
import com.hyz.o2o.entity.ProductCategory;
import com.hyz.o2o.exceptions.ProductCategoryOperationException;

public interface ProductCategoryService {
	List<ProductCategory> getProductCategoryList(long shopId);

	ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws ProductCategoryOperationException;
}
