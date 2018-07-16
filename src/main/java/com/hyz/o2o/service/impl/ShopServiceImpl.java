package com.hyz.o2o.service.impl;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyz.o2o.dao.ShopDao;
import com.hyz.o2o.dto.ShopExecution;
import com.hyz.o2o.entity.Shop;
import com.hyz.o2o.enums.ShopStateEnum;
import com.hyz.o2o.exceptions.ShopOperationException;
import com.hyz.o2o.service.ShopService;
import com.hyz.o2o.util.ImageUtil;
import com.hyz.o2o.util.PageCalculator;
import com.hyz.o2o.util.PathUtil;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;

	@Override
	@Transactional
	public ShopExecution addShop(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 空值判断
		if (shop == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋初始值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectdNum = shopDao.insertShop(shop);
			if (effectdNum <= 0) {
				throw new ShopOperationException("店铺创建失败");
			} else {
				if (shopImgInputStream != null) {
					try {
						addShopImg(shop, shopImgInputStream, fileName);
					} catch (Exception e) {
						throw new ShopOperationException("addShopImg error" + e.getMessage());

					}
					int effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						throw new ShopOperationException("更新图片地址失败");
					}
				}
			}
		} catch (Exception e) {
			throw new ShopOperationException("addShop error:" + e.getMessage());
		}
		return new ShopExecution(ShopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, InputStream shopImgInputStream, String fileName) {
		// 获取shop图片目录的相对值路径

		String dest = PathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream, fileName, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		// TODO Auto-generated method stub
		return shopDao.queryByShopId(shopId);
	}

	@Override
	public ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream, String fileName)
			throws ShopOperationException {
		// TODO Auto-generated method stub
		if (shop == null || shop.getShopId() == null) {
			return new ShopExecution(ShopStateEnum.NULL_SHOP, shop);
		} else {
			try {
				// 1.判断是否需要处理图片
				if (shopImgInputStream != null && fileName != null && !"".equals(fileName)) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if (tempShop.getShopImg() != null) {
						ImageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, shopImgInputStream, fileName);
				}
				// 2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new ShopExecution(ShopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new ShopExecution(ShopStateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
				throw new ShopOperationException("modifyshop error:" + e.getMessage());
			}

		}
	}

	@Override
	public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		ShopExecution se = new ShopExecution();
		if (shopList != null) {
			se.setCount(count);
			se.setShopList(shopList);
		} else {
			se.setState(ShopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}
}
