package com.hyz.o2o.service;

import java.io.InputStream;

import com.hyz.o2o.dto.ShopExecution;
import com.hyz.o2o.entity.Shop;
import com.hyz.o2o.exceptions.ShopOperationException;

public interface ShopService {
	ShopExecution addShop(Shop shop,InputStream shopImgInputStream,String fileName) throws ShopOperationException;
}
