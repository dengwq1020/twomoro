package com.xiyi.manager.controller;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.xiyi.commons.base.BaseController;
import com.xiyi.commons.utils.ImageUploadTool;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.manager.service.iface.IAdminService;


/**
 * 后台管理系统控制层
 * @author wyx
 *
 */
@Controller
@RequestMapping(value="/taomuManager")
public class AdminController extends BaseController {
	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	private IAdminService adminService;
	//上传路径
  	@Value("${upload.path}")
  	private String uploadPath;


	/**
	 * 广告管理
	 */
	@RequestMapping(value="/advertisement")
	public ModelAndView advertisementManager(){
		return new ModelAndView("admin/advertisement");
	}
	/**
	 * 楼层管理
	 */
	@RequestMapping(value="/floor")
	public ModelAndView floorManager(){
		return new ModelAndView("admin/floor");
	}
	/**
	 * 用户管理
	 */
	@RequestMapping(value="/user")
	public ModelAndView user(){
		return new ModelAndView("admin/user");
	}
	/**
	 * 商品管理
	 */
	@RequestMapping(value="/product")
	public ModelAndView productManager(){
		return new ModelAndView("admin/product");
	}
	
	
	/**
	 * 订单管理管理
	 */
	@RequestMapping(value="/orderManager")
	public ModelAndView orderManager(){
		return new ModelAndView("admin/orderManager");
	}
	/**
	 * 用户角色管理
	 */
	@RequestMapping(value="/userManager")
	public ModelAndView userManager(){
		return new ModelAndView("admin/userManager");
	}
	/**
	 * 商户注册审核
	 */
	@RequestMapping(value="/registerAudit")
	public ModelAndView registerAudit(){
		return new ModelAndView("admin/registerAudit");
	}
	/**
	 *	意见反馈管理
	 */
	@RequestMapping(value="/adviceManager")
	public ModelAndView adviceManager(){
		return new ModelAndView("admin/adviceManager");
	}
	/**
	 * 分销规则定义
	 */
	@RequestMapping(value="/rules")
	public ModelAndView rulesManager(){
		return new ModelAndView("admin/rules");
	}
	/**
	 * 数据统计
	 */
	@RequestMapping(value="/dataCount")
	public ModelAndView dataCount(){
		return new ModelAndView("admin/dataCount");
	}
	/**
	 * 自定义消息推送
	 */
	@RequestMapping(value="/pushMessage")
	public ModelAndView pushMessage(){
		return new ModelAndView("admin/pushMessage");
	}
	/**
	 * 抢购活动管理
	 */
	@RequestMapping(value="/acticityManager")
	public ModelAndView acticityManager(){
		return new ModelAndView("admin/acticityManager");
	}
	/**
	 * 商品上架审核
	 */
	@RequestMapping(value="/shelfAudit")
	public ModelAndView shelfAudit(){
		return new ModelAndView("admin/shelfAudit");
	}
}
