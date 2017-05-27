package com.xiyi.manager.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xiyi.commons.base.BaseController;
import com.xiyi.commons.utils.DateUtils;
import com.xiyi.commons.utils.ImageUploadTool;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.commons.utils.StringUtils;
import com.xiyi.commons.utils.UploadUtils;
import com.xiyi.manager.service.iface.IProductService;
import com.xiyi.pojo.Result;

@Controller
@RequestMapping(value="/taomuManager")
public class ProductController extends BaseController {
	
	
public static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private IProductService productService;
	//上传路径
  	@Value("${upload.path}")
  	private String uploadPath;
	/**
	 * 商品分类
	 * @return
	 */
	@RequestMapping(value="/productType")
	public ModelAndView productType(){
		return new ModelAndView("product/productType");
	}

	/**
	 * 获取商品分类
	 * @param page	页面大小
	 * @param rows	显示的记录
	 * @param sort	排序字段
	 * @param order 排序
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getProductType")
	public Object productType(HttpServletRequest request,int page,int rows,String sort,String order){
		
		PageInfo  pageInfo=null;
		try {
			//获取商品分类
		     pageInfo=new PageInfo(page,rows,sort,order);
			 Map<String, Object> condition = new HashMap<String, Object>();
		     if (StringUtils.isNotBlank(request.getParameter("name"))) {
		            condition.put("name", request.getParameter("name"));
		            System.out.println(condition.get("name"));
		        }
		     pageInfo.setCondition(condition);
			 productService.getProductTypeList(pageInfo);
		} catch (Exception e) {
			logger.error("获取商品分类失败",e);
			logger.info("productType接口参数:"+pageInfo);
			return pageInfo;
		}
		return pageInfo;
	}
	
	/**
	 * add商品分类页
	 * @return
	 */
	@RequestMapping(value="/productTypeAdd")
	public ModelAndView productTypeAdd(){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			//获取商品分类id,name,pid
			List<Map<String,Object>> typeList=productService.selectProductType();
			map.put("typeList", typeList);
		} catch (Exception e) {
			logger.error("获取商品分类id,name,pid失败",e);
			return new ModelAndView("product/productType");
		}
		return new ModelAndView("product/productTypeAdd",map);
	}
	/**
	 * add商品分类
	 * @return
	 * @throws  
	 */
	@ResponseBody
	@RequestMapping(value="/addProductType", method = {RequestMethod.GET,RequestMethod.POST})
	public Object AddproductType(MultipartHttpServletRequest request,HttpServletResponse response){
		Result result=new Result() ;
		String message="";
		try {
			Map<String,Object> paramMap=null;
			if(request instanceof MultipartHttpServletRequest){
				//ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
				//CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
				//MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest()); 
				Map<String, String> imgMap =  UploadUtils.multiFileUpload(request,uploadPath,null);
				Enumeration<String> it = request.getParameterNames();
				paramMap = new HashMap<String, Object>();
		        while (it.hasMoreElements()) {
		            String key = it.nextElement();
		            Object value = request.getParameter(key);
		            paramMap.put(key, value);
		            if (logger.isDebugEnabled()) {
		                logger.debug("请求获得参数");
		                logger.debug("key=" + key + ",value=" + value);
		            }
		        }
				if(!"".equals(imgMap.get("imgFile").toString())){
					paramMap.put("img", imgMap.get("imgFile"));
				}
			}
			//paramMap.put("name", request.getParameter("name").toString());
			String time=DateUtils.getDate();
			paramMap.put("create_time", time);
			paramMap.put("update_time", time);
			//paramMap.put("pid", Integer.parseInt(request.getParameter("pid").toString()));
			//paramMap.put("description", request.getParameter("description").toString());
			result=productService.addProductType(paramMap);
		} catch (Exception e) {
			logger.error("商品分类添加失败",e);
			return renderSuccess(message);
		}		
		return renderSuccess(message);
	}
	
	/**
	 * 编辑商品分类页
	 * @return
	 */
	@RequestMapping(value="/productTypeEdit")
	public ModelAndView productTypeEdit(Long id){
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			//根据分类id获取商品分类信息
			map=productService.getProductById(id);
			//获取商品分类列表
			List<Map<String,Object>> typeList=productService.selectProductType();
			map.put("typeList", typeList);
		} catch (Exception e) {
			logger.error("根据分类id获取商品分类信息失败",e);
		}
		
		return new ModelAndView("product/productTypeEdit",map);
	}
	
	/**
	 * 编辑商品分类
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/editProductType",method={RequestMethod.POST,RequestMethod.GET})
	public Object editProductType(MultipartHttpServletRequest request,HttpServletResponse response){
		Result result=new Result();
		try {
			Map<String,Object> paramMap=new HashMap<String, Object>();
			if(request instanceof MultipartHttpServletRequest){
				//ShiroHttpServletRequest shiroRequest = (ShiroHttpServletRequest) request;
				//CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();  
				//MultipartHttpServletRequest multipartRequest = commonsMultipartResolver.resolveMultipart((HttpServletRequest) shiroRequest.getRequest()); 
				Map<String, String> imgMap =  UploadUtils.multiFileUpload(request,uploadPath,null);
				Enumeration<String> it = request.getParameterNames();
		        while (it.hasMoreElements()) {
		            String key = it.nextElement();
		            Object value = request.getParameter(key);
		            paramMap.put(key, value);
		            if (logger.isDebugEnabled()) {
		                logger.debug("请求获得参数");
		                logger.debug("key=" + key + ",value=" + value);
		            }
		        }
				if(!"".equals(imgMap.get("imgs").toString())){
					paramMap.put("img", imgMap.get("imgs"));
				}
			}
		paramMap.put("update_time", DateUtils.getDate());
		result=productService.updateProductType(paramMap);
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
	 * 商品分类修改/添加
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/goodsTypeEdit",method={RequestMethod.POST,RequestMethod.GET})
	public Object goodsTypeEdit(MultipartHttpServletRequest request){
		Result result=new Result();
		try {
			Map<String,Object> paramMap=null;
			if(request instanceof MultipartHttpServletRequest){
				Map<String,String> imgMap= UploadUtils.multiFileUpload(request, uploadPath, null);
				paramMap=getMapParameter(request);
				if(!"".equals(imgMap.get("imgs").toString())){
					paramMap.put("imgs", imgMap.get("imgs"));
				}
				/*if(!"".equals(imgMap.get("thumb").toString())){
					paramMap.put("thumb", imgMap.get("thumb"));
				}*/
			}
			result=productService.goodsTypeEdit(paramMap);
			result.setCode(0);
			result.setMsg("成功");
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("商品分类信息修改失败",e);
			result.setCode(101);
			result.setMsg("系统繁忙,请稍后再试!");
			result.setSuccess(false);
			return result;
		}
		return result;
	}
	protected Map<String, Object> getMapParameter(MultipartHttpServletRequest multipartRequest) {
	    	Enumeration<String> it = multipartRequest.getParameterNames();
	        Map<String, Object> re = new HashMap<String, Object>();
	        while (it.hasMoreElements()) {
	            String key = it.nextElement();
	            Object value = multipartRequest.getParameter(key);
	            re.put(key, value);
	            if (logger.isDebugEnabled()) {
	                logger.debug("请求获得参数");
	                logger.debug("key=" + key + ",value=" + value);
	            }
	        }
	        return re;
	}
	
	/**
	 * delete商品分类
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/productTypeDelete")
	public Object productTypeDelete(Long id){
		String message=productService.deleteProductType(id);
		return renderSuccess(message);
	}

	/**
	 * 商品列表
	 * @return
	 */
	@RequestMapping(value="/productList")
	public ModelAndView productList(){
		return new ModelAndView("product/productList");
	}

	/**
	 * 获取商品列表
	 * @param page	当前页
	 * @param rows	显示的记录
	 * @param sort	排序字段
	 * @param order 排序
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getProductList")
	public Object getProductList(HttpServletRequest request,int page,int rows,String sort,String order){
		PageInfo pageInfo=null;
		try {
			//获取商品列表
			pageInfo=new PageInfo(page,rows,sort,order);
			Map<String, Object> condition = new HashMap<String, Object>();
		        if (StringUtils.isNotBlank(request.getParameter("name"))) {
		            condition.put("name", request.getParameter("name"));
		            System.out.println(condition.get("name"));
		        }
		    pageInfo.setCondition(condition);
			productService.getProducList(pageInfo);
		} catch (Exception e) {
			logger.error("获取商品列表失败",e);
		}
		return pageInfo;
	}
	
	/**
	 * delete商品
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/productDelete")
	public Object productDelete(Long id){
		String message=productService.deleteProduct(id);
		return renderSuccess(message); 
	}
	
	/**
	 * 商品发布
	 */
	@RequestMapping(value="/publicProduct")
	public ModelAndView publicProduct(){
		return new ModelAndView("product/publicProduct");
	}
}
