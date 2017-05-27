package com.xiyi.manager.service.iface;

import java.util.Map;

import com.xiyi.commons.utils.PageInfo;
import com.xiyi.pojo.Result;

public interface IUserExService {

	/**
	 * 获取用户信息列表
	 * @param pageInfo
	 */
	void selectDataGrid(PageInfo pageInfo);
	
	/**
	 * 查询用户信息 by id
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectUserById(Long id);
}
