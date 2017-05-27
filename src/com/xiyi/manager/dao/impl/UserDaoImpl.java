package com.xiyi.manager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiyi.commons.utils.PageInfo;
import com.xiyi.manager.dao.JdbcBaseDao;
import com.xiyi.manager.dao.iface.IUserDao;

@Repository
public class UserDaoImpl extends JdbcBaseDao implements IUserDao {

	@Override
	public List<Map<String, Object>> selectUser(PageInfo pageInfo) {
		String sql = "select * from b_user limit :pageNow,:pageSize" ;
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("pageNow", pageInfo.getFrom());
		map.put("pageSize", pageInfo.getSize());
		return this.queryForList("b_user",sql,map);
	}

	@Override
	public int deleteUser(Long id) {
		String sql = "delete from b_user where id=?";
		return this.update(sql,new Object[]{id});
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectUserByid(Long id) {
		String sql = "select * from b_user where id=? limit 1";
		List<?> list = this.queryForList(sql,
				new Object[] { id });
		if (list != null && list.size() > 0) {
			return (Map<String, Object>) list.get(0);
		} else {
			return null;
		}
	}

	

}
