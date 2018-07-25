package com.hyz.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyz.o2o.dao.ProductDao;
import com.hyz.o2o.dao.ProductImgDao;
import com.hyz.o2o.dto.ImageHolder;
import com.hyz.o2o.dto.ProductExecution;
import com.hyz.o2o.entity.Product;
import com.hyz.o2o.entity.ProductImg;
import com.hyz.o2o.enums.ProductStateEnum;
import com.hyz.o2o.exceptions.ProductOperateionException;
import com.hyz.o2o.service.ProductService;
import com.hyz.o2o.util.ImageUtil;
import com.hyz.o2o.util.PageCalculator;
import com.hyz.o2o.util.PathUtil;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductImgDao productImgDao;

	@Override
	public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);

		int count = productDao.queryProductCount(productCondition);

		ProductExecution pe = new ProductExecution();

		pe.setProductList(productList);

		pe.setCount(count);

		return pe;
	}

	@Override
	public Product getProductById(long productId) {

		return productDao.queryProductById(productId);
	}

	@Override
	@Transactional
	// 1.处理缩略图，获取缩略图相对路径并赋值给product
	// 2.往tb_product写入商品信息,获取productId
	// 3.结合productId批量处理商品详情图片
	// 4.将商品详情图列表批量插入tb_product_img
	public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList)
			throws ProductOperateionException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());

			product.setEnableStatus(1);

			if (thumbnail != null) {
				addThumbnail(product, thumbnail);
			}
			try {
				// 创建商品信息
				int effectedNum = productDao.insertProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperateionException("创建商品失败");
				}
			} catch (Exception e) {
				throw new ProductOperateionException("创建商品失败:" + e.toString());
			}
			// 若商品详情图不为空则添加
			if (productImgList != null && productImgList.size() > 0) {
				addProductImgList(product, productImgList);
			}
			return new ProductExecution(ProductStateEnum.SUCCESS, product);
		} else {
			// 传参为空则返回控制错误信息
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	/**
	 * 添加缩略图
	 * 
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product, ImageHolder thumbnail) {
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}

	private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
		// 获取图片存储路径，这里直接存放到相应的店铺的文件夹下
		String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
		List<ProductImg> productImgList = new ArrayList<ProductImg>();

		// 遍历图片一次去处理，并添加进productImg实体类中
		for (ImageHolder productImgHolder : productImgHolderList) {
			String imgAddr = ImageUtil.generalNormalImg(productImgHolder, dest);
			ProductImg productImg = new ProductImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}

		// 如果确实是有图片需要添加的，就执行批量添加操作
		if (productImgList.size() > 0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if (effectedNum <= 0) {
					throw new ProductOperateionException("创建图片详情图片失败");
				}
			} catch (Exception e) {
				throw new ProductOperateionException("创建图片详情图片失败：" + e.toString());
			}
		}
	}

	@Override
	public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
			List<ImageHolder> productImgHolderList) throws ProductOperateionException {
		// 1。若缩略图参数有值。则处理缩略图
		// 2 若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
		// 3 将tb_product_img 下面的该商品原先的商品详情图记录全部清除
		// 4 更新tb_product的信息
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			product.setLastEditTime(new Date());

			if (thumbnail != null) {
				Product tempProduct = productDao.queryProductById(product.getProductId());
				if (tempProduct.getImgAddr() != null) {
					ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}

			if (productImgHolderList != null && productImgHolderList.size() > 0) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}

			try {
				int effectedNum = productDao.updateProduct(product);
				if (effectedNum <= 0) {
					throw new ProductOperateionException("更新商品信息失败");
				}
				return new ProductExecution(ProductStateEnum.SUCCESS, product);

			} catch (Exception e) {
				throw new ProductOperateionException("更新商品信息失败：" + e.toString());
			}
		} else {
			return new ProductExecution(ProductStateEnum.EMPTY);
		}
	}

	private void deleteProductImgList(long productId) {
		List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);

		for (ProductImg productImg : productImgList) {
			ImageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		productImgDao.deleteProductImgByProductId(productId);
	}

}
