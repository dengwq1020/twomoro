package com.xiyi.manager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.manager.dao.iface.IUserDao;
import com.xiyi.manager.service.iface.IUserExService;
import com.xiyi.model.vo.UserVo;
import com.xiyi.pojo.Result;
@Service
public class UserExServiceImpl implements IUserExService {
	private static final Logger logger = LoggerFactory.getLogger(UserExServiceImpl.class);
	@Autowired
	IUserDao userDaoImpl;
	@Override
	public void selectDataGrid(PageInfo pageInfo) {
		Page page = new Page(pageInfo.getNowpage(), pageInfo.getSize());
        List<Map<String, Object>> list = userDaoImpl.selectUser(pageInfo);
        pageInfo.setRows(list);
        pageInfo.setTotal(list.size());
	}
	@Override
	public Map<String, Object> selectUserById(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		Result result = new Result();
		try {
			map = userDaoImpl.selectUserByid(id);
			result.setData(map);
		} catch (Exception e) {
			logger.error("selectUserById error",e);;
		}
		return map;
	}

}
