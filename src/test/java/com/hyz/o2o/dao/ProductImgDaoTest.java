package com.hyz.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.hyz.o2o.BaseTest;
import com.hyz.o2o.entity.ProductImg;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductImgDaoTest extends BaseTest {
	@Autowired
	ProductImgDao productImgDao;

	@Test
	public void testABatchInsertProductImg() throws Exception {
		ProductImg productImg = new ProductImg();
		productImg.setImgAddr("图片1");
		productImg.setImgDesc("测试图片1");
		productImg.setPriority(1);
		productImg.setProductId(2L);
		productImg.setCreateTime(new Date());

		ProductImg productImg2 = new ProductImg();
		productImg2.setImgAddr("图片2");
		productImg2.setImgDesc("测试图片2");
		productImg2.setPriority(2);
		productImg2.setProductId(2L);
		productImg2.setCreateTime(new Date());

		List<ProductImg> productImgList = new ArrayList<ProductImg>();
		productImgList.add(productImg);
		productImgList.add(productImg2);

		int effectedNum = productImgDao.batchInsertProductImg(productImgList);
		assertEquals(2, effectedNum);

	}

	@Test
	public void testBQueryProductImgList() throws Exception {
		List<ProductImg> productImgs = productImgDao.queryProductImgList(2L);
		assertEquals(2, productImgs.size());
	}

	@Test
	public void testCDeleteProductImgByProductId() throws Exception {
		int effectedNum = productImgDao.deleteProductImgByProductId(2L);
		assertEquals(2, effectedNum);
	}
}
