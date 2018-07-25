package com.hyz.o2o.service;

import java.util.List;

import com.hyz.o2o.dto.ImageHolder;
import com.hyz.o2o.dto.ProductExecution;
import com.hyz.o2o.entity.Product;
import com.hyz.o2o.exceptions.ProductOperateionException;

public interface ProductService {
	ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperateionException;

	/**
	 * 
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

	/**
	 * 
	 * @param productId
	 * @return
	 */
	Product getProductById(long productId);

	ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperateionException;
}
