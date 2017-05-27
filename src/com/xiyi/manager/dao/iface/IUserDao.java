package com.xiyi.manager.dao.iface;

import java.util.List;
import java.util.Map;

import com.xiyi.commons.utils.PageInfo;


public interface IUserDao {

	/**
	 * 查询用户信息列表
	 * @param pageInfo
	 * @return
	 */
	public List<Map<String,Object>> selectUser(PageInfo pageInfo);
	
	/**
	 * 根据id删除用户
	 * @param id
	 * @return
	 */
	public int deleteUser(Long id);
	
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	public Map<String, Object> selectUserByid(Long id);
	
	
}
