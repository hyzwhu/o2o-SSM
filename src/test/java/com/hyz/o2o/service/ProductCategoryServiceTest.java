package com.hyz.o2o.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyz.o2o.BaseTest;
import com.hyz.o2o.entity.ProductCategory;

public class ProductCategoryServiceTest extends BaseTest {
	@Autowired
	private ProductCategoryService productCategoryService;

	@Test
	public void testGetProductCategoryList() {
		List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(20L);
		System.out.println(productCategoryList.get(0).getProductCategoryName());
	}
}
