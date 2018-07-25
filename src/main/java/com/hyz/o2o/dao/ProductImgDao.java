package com.hyz.o2o.dao;

import java.util.List;

import com.hyz.o2o.entity.ProductImg;

public interface ProductImgDao {
	List<ProductImg> queryProductImgList(long productId);

	int batchInsertProductImg(List<ProductImg> ProductImgList);

	int deleteProductImgByProductId(long productId);
}
