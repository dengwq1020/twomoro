package com.xiyi.manager.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyi.commons.utils.DateUtils;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.manager.dao.iface.IProductDao;
import com.xiyi.manager.service.iface.IProductService;
import com.xiyi.pojo.Result;
@Service
public class ProductServiceImpl implements IProductService {
private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private IProductDao productDao;
	
	/**
	 * 获取商品分类
	 */
	@Override
	public void getProductTypeList(PageInfo pageInfo){
		try {
			//获取商品分类
			List<Map<String,Object>> list=productDao.getProductTypeList(pageInfo);
			if(!list.isEmpty()){	
		        pageInfo.setTotal((Integer)list.get(0).get("totalNum"));
			}
			for (int i = 0; i < list.size(); i++) {
				String createTime=list.get(i).get("create_time").toString();
				String create_time=DateUtils.getNewDate(createTime);
				String updateTime=list.get(i).get("update_time").toString();
			    String update_time=DateUtils.getNewDate(updateTime);
			    list.get(i).put("create_time", create_time);
			    list.get(i).put("update_time", update_time);
			}
			pageInfo.setRows(list);
		} catch (Exception e) {
			logger.error("获取商品分类失败",e);
			logger.info("getProductTypeList方法参数:"+pageInfo);
		}
	}
	
	/**
	 * delete商品分类
	 */
	@Override
	public String deleteProductType(Long id) {
		String message="";
		List<Map<String,Object>> map=productDao.selectProductList(id);
		if(!map.isEmpty()){
			message="请先删除商品分类中的商品!";
			return message;
		}
		List<Map<String,Object>> mapList=productDao.byProductType(id);
		if(!mapList.isEmpty()){
			message="请先删除子级!";
			return message;
		}
		int row=productDao.deleteProductType(id);
		if(row>0){
			message="商品分类删除成功！";
		}else{
			message="商品分类删除失败！";
		}
		return message;
	}
	
	/**
	 * 获取商品列表
	 */
	@Override
	public void getProducList(PageInfo pageInfo) {
		try {
			//获取商品列表
			List<Map<String,Object>> list=productDao.getProducList(pageInfo);
			pageInfo.setRows(list);
/*			for (int i = 0; i < list.size(); i++) {
				String createTime=list.get(i).get("create_time").toString();
				String create_time=DateUtils.getNewDate(createTime);
				String updateTime=list.get(i).get("update_time").toString();
			    String update_time=DateUtils.getNewDate(updateTime);
			    list.get(i).put("create_time", create_time);
			    list.get(i).put("update_time", update_time);
			}
*/			pageInfo.setTotal((Integer)list.get(0).get("totalNum"));
		} catch (Exception e) {
			logger.error("获取商品列表失败",e);
		}
	}
	
	/**
	 * delete商品
	 */
	@Override
	public String deleteProduct(Long id) {
		
		String message="";
	
		int row=productDao.deleteProduct(id);
		if(row>0){
			message="商品删除成功！";
		}else{
			message="商品删除失败,上架和待审核商品必需下架才可删除！";
		}
		return message;
	}
	
	/**
	 * 查询商品分类id，name,pid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> selectProductType() {
		List<Map<String,Object>> list=null;
		try {
			list=productDao.selectProductType();
		} catch (Exception e) {
			logger.error("查询商品分类信息获取失败",e);
			return list;
		}
		
		return list;
	}
	
	/**
	 * add商品分类
	 */
	@Override
	public Result addProductType(Map<String, Object> paramMap) {		
		Result result=new Result();
		try {
			int row=productDao.addProductType(paramMap);
			if(row<=0){
				result.setMsg("新增商品分类失败!");
				result.setData(101);
				result.setSuccess(false);
				return result;
			}
			result.setCode(0);
			result.setMsg("成功");
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类添加失败",e);
		}
		return result;
	}
	
	/**
	 * 编辑商品分类
	 */
	@Override
	public Result updateProductType(Map<String, Object> paramMap) {		
		Result result=new Result();
		try {
			int row=productDao.updateProductType(paramMap);
			if(row<=0){
				result.setMsg("新增商品分类失败!");
				result.setData(101);
				result.setSuccess(false);
				return result;
			}
			result.setCode(0);
			result.setMsg("成功");
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类添加失败",e);
			result.setCode(101);
			result.setMsg("系统繁忙!请稍后再试!");
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	/**
	 * 根据分类id获取商品分类信息
	 * @param id
	 * @return
	 */
	@Override
	public Map<String, Object> getProductById(Long id) {
		Map<String,Object> map=null;
		try {
			map=productDao.getProductById(id);
		} catch (Exception e) {
			logger.error("根据分类id获取商品分类信息失败",e);
			return map;
		}
		return map;
	}

	@Override
	public Result goodsTypeEdit(Map<String, Object> paramMap) {
		Result result=new Result();
		String message="";
		try {
			if(!"".equals((String)paramMap.get("id"))){
				//修改商品
				message="修改";
				paramMap.put("update_time", DateUtils.getDate());
				int row=productDao.updateProductType(paramMap);
				if(row>0){
					result.setCode(0);
					result.setMsg("成功!");
					result.setSuccess(true);
					return result;
				}
			}else{
				//添加商品
				message="添加";
				paramMap.put("create_time", DateUtils.getDate());
				paramMap.put("update_time", DateUtils.getDate());
				int row=productDao.addProductType(paramMap);
				if(row>0){
					result.setCode(0);
					result.setMsg("成功!");
					result.setSuccess(true);
					return result;
				}	
			}
			result.setCode(101);
			result.setMsg("系统繁忙,请稍候再试!");
			result.setSuccess(false);
		} catch (Exception e) {
			logger.error("商品分类"+message+"失败!",e);
			result.setCode(101);
			result.setMsg("系统繁忙,请稍候再试!");
			result.setSuccess(false);
		}
		return result;
	}
	

	
	
}
