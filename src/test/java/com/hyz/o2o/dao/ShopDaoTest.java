package com.hyz.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyz.o2o.BaseTest;
import com.hyz.o2o.entity.Area;
import com.hyz.o2o.entity.PersonInfo;
import com.hyz.o2o.entity.Shop;
import com.hyz.o2o.entity.ShopCategory;
import com.hyz.o2o.web.superadmin.AreaController;

public class ShopDaoTest extends BaseTest {
	@Autowired
	private ShopDao shopDao;

	@Test
	@Ignore
	public void testInsertShop() {
		Logger logger = LoggerFactory.getLogger(AreaController.class);
		Shop shop = new Shop();
		PersonInfo owner = new PersonInfo();
		ShopCategory shopCategory = new ShopCategory();
		Area area = new Area();
		owner.setUserId(1L);
		area.setAreaId(2);
		shopCategory.setShopCategoryId(1L);
		shop.setOwner(owner);
		shop.setArea(area);
		shop.setShopCategory(shopCategory);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("test");
		shop.setShopAddr("test");
		shop.setPhone("test");
		shop.setShopImg("test");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		try {
			int TestValue = shopDao.insertShop(shop);
			assertEquals(1, TestValue);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	@Test
	public void testUpdateShop() {
		Shop shop = new Shop();
		shop.setShopId(5L);
		shop.setShopName("测试的店铺");
		shop.setShopDesc("测试的店铺");
		shop.setShopAddr("测试的店铺");
		shop.setLastEditTime(new Date());
		shop.setAdvice("审核通过");
		int TestValue = shopDao.updateShop(shop);
		assertEquals(1, TestValue);
	}
}
