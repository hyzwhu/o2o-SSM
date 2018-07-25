package com.hyz.o2o.service;

import java.io.InputStream;

import com.hyz.o2o.dto.ImageHolder;
import com.hyz.o2o.dto.ShopExecution;
import com.hyz.o2o.entity.Shop;
import com.hyz.o2o.exceptions.ShopOperationException;

public interface ShopService {

	/**
	 * 根据shopConditin分页返回相应列表数据
	 * 
	 * @param shopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

	Shop getByShopId(long shopId);

	ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

	ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

}
