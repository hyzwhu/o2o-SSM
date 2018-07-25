package com.hyz.o2o.service;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyz.o2o.BaseTest;
import com.hyz.o2o.dto.ImageHolder;
import com.hyz.o2o.dto.ShopExecution;
import com.hyz.o2o.entity.Area;
import com.hyz.o2o.entity.PersonInfo;
import com.hyz.o2o.entity.Shop;
import com.hyz.o2o.entity.ShopCategory;
import com.hyz.o2o.enums.ShopStateEnum;
import com.hyz.o2o.exceptions.ShopOperationException;

public class ShopServiceTest extends BaseTest {
	@Autowired
	private ShopService shopService;

	@Test
	public void testQueryShopList() {
		Shop shopCondition = new Shop();
		ShopCategory shopCategory = new ShopCategory();
		shopCategory.setShopCategoryId(2l);
		shopCondition.setShopCategory(shopCategory);
		ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
		System.out.println("店铺分页操作后总数为:" + se.getShopList().size());
		System.out.println("店铺总数为：" + se.getCount());
	}

	@Test
	@Ignore
	public void testModifyShopId() throws ShopOperationException, FileNotFoundException {
		Shop shop = shopService.getByShopId(20L);
		shop.setShopName("修改后的店铺名称");
		File shopImg = new File("/Users/yogi/Documents/hyz-doc/image/t016c3e238261049d91.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("t016c3e238261049d91.jpg", is);
		ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
		System.out.println("新的图片地址:" + shopExecution.getShop().getShopImg());
	}

	@Test
	@Ignore
	public void testAddShop() throws FileNotFoundException {
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
		shop.setShopName("测试的店铺4");
		shop.setShopDesc("test3");
		shop.setShopAddr("test3");
		shop.setPhone("test3");
		shop.setCreateTime(new Date());
		shop.setLastEditTime(new Date());
		shop.setEnableStatus(1);
		shop.setAdvice("审核中");
		File shopImg = new File("/Users/yogi/Documents/hyz-doc/image/t016122ec45c5fdc5fa.jpg");
		InputStream is = new FileInputStream(shopImg);
		ImageHolder imageHolder = new ImageHolder("/Users/yogi/Documents/hyz-doc/image/t016122ec45c5fdc5fa.jpg", is);
		ShopExecution se = shopService.addShop(shop, imageHolder);
		assertEquals(ShopStateEnum.CHECK.getState(), se.getState());

	}
}
