package com.xiyi.manager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.xiyi.commons.utils.PageInfo;
import com.xiyi.manager.dao.JdbcBaseDao;
import com.xiyi.manager.dao.iface.IProductDao;

@Repository
public class ProductDaoImpl extends JdbcBaseDao implements IProductDao {

	@Override
	public List<Map<String, Object>> getProductTypeList(PageInfo pageInfo) {
		String sql="select * from b_product_type ";
		Map<String,Object> map=new HashMap<String,Object>();
		if(pageInfo.getCondition().containsKey("name")){
			sql+=" where (name like concat('%',:name,'%'))";
			map.put("name", pageInfo.getCondition().get("name").toString());
		}
		if(pageInfo.getOrder()!=null && !pageInfo.getOrder().equals("")){
			sql+=" order by "+pageInfo.getSort()+" "+pageInfo.getOrder();
//			sql+=" order by concat(' ',:sort,:order)";
//			map.put("sort", pageInfo.getSort());
//			map.put("order", pageInfo.getOrder());
		}
		sql+=" limit :page,:limit";
		map.put("page", pageInfo.getFrom());
		map.put("limit", pageInfo.getPagesize());
	
		return this.queryForList("b_product_type",sql,map);
	}

	@Override
	public List<Map<String, Object>> getProducList(PageInfo pageInfo) {
		String sql="select * from b_product";
		Map<String,Object> map=new HashMap<String,Object>();
		if(pageInfo.getCondition().containsKey("name")){
			sql+=" where (name like concat('%',:name,'%'))";
			map.put("name", pageInfo.getCondition().get("name").toString());
		}
		if(pageInfo.getOrder()!=null && !pageInfo.getOrder().equals("")){
			sql+=" order by "+pageInfo.getSort()+" "+pageInfo.getOrder();
		}
		sql+=" limit :page,:limit";
		map.put("page", pageInfo.getFrom());
		map.put("limit", pageInfo.getPagesize());
		return this.queryForList("b_product",sql,map);
	}

	@Override
	public int deleteProductType(Long id) {
		String sql="delete from b_product_type where id=?";
		return this.update(sql, new Object[]{id});
	}

	@Override
	public int deleteProduct(Long id) {
		String sql="delete from b_product where id=? and status not in(1,5)";
		return this.update(sql, new Object[]{id});
	}
	
	@Override
	public List<Map<String,Object>> selectProductList(Long id){
		String sql="select * from b_product where p_type_id=?";
		return this.queryForList(sql, new Object[]{id});
	}
	
	@Override
	public List<Map<String,Object>> selectProductType(){
		String sql="select id,pid,name from b_product_type where status=1 and pid=0";
		return this.queryForList(sql);
	}

	public Map<String,Object> getProductById(Long id){
		String sql="select * from b_product_type where id=?";
		return this.queryForMap(sql,new Object[]{id});
	}
	
	@Override
	public int addProductType(Map<String,Object> paramMap) {
		String sql="insert into  b_product_type(name,create_time,update_time,description,pid,img,status) values(:name,:create_time,:update_time,:description,:pid,:img,1)";
		return this.update(sql, paramMap);
	}
	
	@Override
	public int updateProductType(Map<String, Object> paramMap) {
		String sql="update b_product_type set name=:name,update_time=:update_time,description=:description,pid=:pid,status=:status,img=:img where id=:id";
		return this.update(sql, paramMap);
	}

	@Override
	public List<Map<String, Object>> byProductType(Long id) {
		String sql="select * from b_product where pid=?";
		return this.queryForList(sql, new Object[]{id});
	}
}
