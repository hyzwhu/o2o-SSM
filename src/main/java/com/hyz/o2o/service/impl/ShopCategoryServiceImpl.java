package com.hyz.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyz.o2o.dao.ShopCategoryDao;
import com.hyz.o2o.entity.ShopCategory;
import com.hyz.o2o.service.ShopCategoryService;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
	
	@Autowired
	ShopCategoryDao shopCategoryDao;
	
	@Override
	public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
		// TODO Auto-generated method stub
		
		return shopCategoryDao.queryShopCategory(shopCategoryCondition);
	}

}
