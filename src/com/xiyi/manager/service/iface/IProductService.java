package com.xiyi.manager.service.iface;

import java.util.List;
import java.util.Map;

import com.xiyi.commons.utils.PageInfo;
import com.xiyi.pojo.Result;
/**
 * 商品接口
 * @author Administrator
 *
 */

public interface IProductService {

	/**
	 * 获取商品分类
	 * @return
	 */
	void getProductTypeList(PageInfo pageInfo);
	
	
	/**
	 * delete商品分类
	 * @param id
	 */
	String deleteProductType(Long id);
	
	
	/**
	 * 获取商品列表
	 * @param pageInfo
	 */
	void getProducList(PageInfo pageInfo);
	
	/**
	 * delete商品
	 * @param id
	 * @return
	 */
	String deleteProduct(Long id);
	/**
	 * 查询商品分类id，商品分类名称，商品分类父id
	 * @return
	 */
	List<Map<String,Object>> selectProductType();
	
	/**
	 * add商品分类
	 * @param paramMap
	 * @return
	 */
	Result addProductType(Map<String,Object> paramMap);
	
	/**
	 * 修改商品分类
	 * @param paramMap
	 * @return
	 */
	Result updateProductType(Map<String, Object> paramMap);
	
	/**
	 * 商品分类修改/添加
	 * @param paramMap
	 * @return
	 */
	Result goodsTypeEdit(Map<String,Object> paramMap);
	/**
	 * 根据分类id获取商品分类信息
	 * @param id
	 * @return
	 */
	Map<String,Object> getProductById(Long id);
	

}
