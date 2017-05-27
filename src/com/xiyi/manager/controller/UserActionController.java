package com.xiyi.manager.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyi.commons.base.BaseController;
import com.xiyi.commons.utils.DigestUtils;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.commons.utils.StringUtils;
import com.xiyi.manager.service.iface.IUserExService;
import com.xiyi.model.User;
import com.xiyi.model.vo.UserVo;
import com.xiyi.pojo.Result;

@Controller
@RequestMapping("/u")
public class UserActionController extends BaseController {
	
	private static final Logger logger =LoggerFactory.getLogger(UserActionController.class);
	@Autowired
	IUserExService userService;
	/**
	 * 查询用户列表
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order
	 * @return
	 */
	@RequestMapping(value="/dataGrid", method = RequestMethod.POST)
    @ResponseBody
    public Object dataGrid(Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows);
        Map<String, Object> condition = new HashMap<String, Object>();
        
        pageInfo.setCondition(condition);
        userService.selectDataGrid(pageInfo);
        return pageInfo;
    }
	
	/**
     * 编辑用户页
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value="/editPage", method = {RequestMethod.POST,RequestMethod.GET})
    public String editPage(Long id,Map<String, Object> map) {
        Map<String, Object> userMap = new HashMap<String, Object>();
        try {
        	userMap = userService.selectUserById(id);
        	map.put("phone", userMap.get("phone"));
        	map.put("status", userMap.get("status"));
        	map.put("type", userMap.get("type"));
        	map.put("id", userMap.get("id"));
		} catch (Exception e) {
			logger.error("editPage error-->",e);
		}
        return "admin/userEdit";
    }
    
    /**
     * 编辑用户
     * @return
     */
    @RequestMapping(value="/edit",method={RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public Object edit(HttpServletRequest request) {
       System.out.println("test----");
        return renderSuccess("修改成功！");
    } 
}
