package com.xiyi.manager.dao.iface;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiyi.commons.utils.PageInfo;


public interface IProductDao {

	/**
	 * 获取商品分类
	 * @return
	 */
	List<Map<String,Object>> getProductTypeList(PageInfo pageInfo);
	
	/**
	 * delete商品分类
	 * @param pageInfo
	 * @return
	 */
	int deleteProductType(Long id);
	
	/**
	 * 获取商品列表
	 * @param pageInfo
	 * @return
	 */
	List<Map<String,Object>> getProducList(PageInfo pageInfo);
	
	/**
	 * delete商品
	 * @param pageInfo
	 * @return
	 */
	int deleteProduct(Long id);
	
	/**
	 * 根据商品分类ID判断是否存在主外键商品
	 * @param id
	 * @return
	 */
	List<Map<String,Object>> selectProductList(Long id);
	
	/**
	 * 根据商品分类ID判断是否有子级
	 * @param id
	 * @return
	 */
	List<Map<String,Object>> byProductType(Long id);
	
	/**
	 * 查询商品分类id，商品分类名称，商品分类父id
	 * @return
	 */
	List<Map<String,Object>> selectProductType();
	
	/**
	 * add商品分类
	 * @return
	 */
	int addProductType(Map<String,Object> paramMap);
	
	/**
	 * 修改商品分类
	 * @return
	 */
	int updateProductType(Map<String,Object> paramMap);
	
	/**
	 * 根据分类id获取商品分类信息
	 * @param id
	 * @return
	 */
	Map<String,Object> getProductById(Long id);
}
