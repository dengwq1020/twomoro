package com.xiyi.manager.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;


public abstract class JdbcBaseDao extends NamedParameterJdbcDaoSupport
{
    private static final Logger logger = LoggerFactory.getLogger(JdbcBaseDao.class);

    /**
     * 事务控制模板
     */
    @Resource(name = "transactionTemplate")
    private TransactionTemplate transactionTemplate;


    /**
     * 注入dataSource
     *
     * @param dataSource
     */
    @Resource(name = "dataSource")
    public void setSuperDataSource(DataSource dataSource)
    {
        setDataSource(dataSource);
    }

    /**
     * 普通DML操作,如insert,update,delete
     *
     * @param sql
     * @return
     */
    public int update(String sql)
    {
        return this.getJdbcTemplate().update(sql);
    }

    /**
     * 普通DML操作,如insert,update,delete
     *
     * @param sql
     * @param params
     * @return
     */
    public int update(String sql, Object[] params)
    {
        return this.getJdbcTemplate().update(sql, params);
    }

    
    public int update(String sql, Map<String, Object> map)
    {
        return this.getNamedParameterJdbcTemplate().update(sql, map);
    }
    
    /**
     * 批量普通DML操作,如insert,update,delete 由于需要预编译多条sql语句,效率低于batchUpdate(final String sql, final List<List<Object>>
     * paramsList)、batchUpdate(String sql, BatchPreparedStatementSetter pss)
     *
     * @param sql
     * @return
     */
    public int[] batchUpdate(String[] sql)
    {
        return this.getJdbcTemplate().batchUpdate(sql);
    }

    /**
     * 批量普通DML操作,如insert,update,delete 由于仅预编译1条sql语句执行效率较高
     *
     * @param sql
     * @param batchArgs
     * @return
     */
    public int[] batchUpdate(final String sql, final List<Object[]> batchArgs)
    {
        Assert.notEmpty(batchArgs, "can not do batchUpdate operation, batchArgs is empty!");
        return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter()
        {
            public void setValues(PreparedStatement ps, int i)
                    throws SQLException
            {
                Object[] params = batchArgs.get(i);
                if (params != null && params.length > 0)
                {
                    for (int k = 0; k < params.length; k++)
                    {
                        Object arg = params[k];
                        if (arg instanceof SqlParameterValue)
                        {
                            SqlParameterValue paramValue = (SqlParameterValue)arg;
                            StatementCreatorUtils.setParameterValue(ps, k + 1, paramValue, paramValue.getValue());
                        }
                        else
                        {
                            StatementCreatorUtils.setParameterValue(ps, k + 1, SqlTypeValue.TYPE_UNKNOWN, arg);
                        }
                    }
                }
            }

            public int getBatchSize()
            {
                return batchArgs.size();
            }
        });
    }

    /**
     * 批量普通DML操作,如insert,update,delete 由于仅预编译1条sql语句执行效率较高
     *
     * @param sql -
     * @param pss
     * @return
     */
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss)
    {
        return this.getJdbcTemplate().batchUpdate(sql, pss);
    }

    /**
     * 单行结果查询 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @param sql
     * @return
     */
    public Map<String, Object> queryForMap(String sql)
    {
        return this.getJdbcTemplate().queryForMap(sql);
    }

    /**
     * 单行结果查询 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @param sql
     * @param params
     * @return
     */
    public Map<String, Object> queryForMap(String sql, Object... params)
    {
        return this.getJdbcTemplate().queryForMap(sql, params);
    }
    
    
    public Map<String, Object> queryForMap(String sql, Map<String, Object> map)
    {
        return this.getNamedParameterJdbcTemplate().queryForMap(sql, map);
    }

    /**
     * 针对单行中某个列的值的查询 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @param <T>
     * @param sql
     * @param requiredType
     * @return
     */
    public <T> T queryForObject(String sql, Class<T> requiredType)
    {
        return (T)this.getJdbcTemplate().queryForObject(sql, requiredType);
    }

    /**
     * 针对单行中某个列的值的查询 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @param <T>
     * @param sql
     * @param params
     * @param requiredType
     * @return
     */
    public <T> T queryForObject(String sql, Object[] params, Class<T> requiredType)
    {
        return (T)this.getJdbcTemplate().queryForObject(sql, params, requiredType);
    }

    /**
     * 单行结果查询 返回的javabean类型由rowMapper决定 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @param <T>
     * @param sql
     * @param rowMapper
     * @return
     */
    public <T> T queryForObject(String sql, RowMapper rowMapper)
    {
        return (T)this.getJdbcTemplate().queryForObject(sql, rowMapper);
    }

    /**
     * 单行结果查询 返回的javabean类型由rowMapper决定 (注：返回的结果行数大于1时将会抛出异常)
     *
     * @see  <code>AutoMatchingRowMapper</code>
     * @param <T>
     * @param sql
     * @param params
     * @param rowMapper
     * @return
     * @throws MyException
     */
    public <T> T queryForObject(String sql, Object[] params, RowMapper rowMapper) throws Exception
    {
        try{
            return (T)this.getJdbcTemplate().queryForObject(sql, params, rowMapper);
        } catch(EmptyResultDataAccessException e) {
            return null;
        } catch(Exception e){
            throw e;
        }
    }

    
    public  <T> T queryForObject(String sql, Map<String, Object> map, RowMapper rowMapper) throws Exception
    { 
		try {
			return (T)this.getNamedParameterJdbcTemplate().queryForObject(sql, map, rowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		} catch (Exception e) {
			throw e;
		}
    }
    
    public  <T> T queryForObject(String sql, Map<String, Object> map, Class<T> requiredType)
    {
        return (T)this.getNamedParameterJdbcTemplate().queryForObject(sql, map, requiredType);
    }
    /**
     * count,sum,max等查询
     *
     * @param sql
     * @return
     */
    public int queryForInt(String sql)
    {
        return this.getJdbcTemplate().queryForObject(sql,Integer.class);
    }

    /**
     * count,sum,max等查询
     *
     * @param sql
     * @param params
     * @return
     */
    public int queryForInt(String sql, Object[] params)
    {
        return this.getJdbcTemplate().queryForObject(sql, params,Integer.class);
    }

    public int queryForInt(String sql, Map<String, Object> map)
    {
        return this.getNamedParameterJdbcTemplate().queryForObject(sql, map,Integer.class);
    }
    
    /**
     * count,sum,max等查询
     *
     * @param sql
     * @param params
     * @return
     */
    public long queryForLong(String sql, Object[] params)
    {
        return this.getJdbcTemplate().queryForObject(sql, params, Long.class);
    }

    public long queryForLong(String sql, Map<String, Object> map)
    {
        return this.getNamedParameterJdbcTemplate().queryForObject(sql, map, Long.class);
    }
    /**
     * count,sum,max等查询
     *
     * @param sql
     * @return
     */
    public long queryForLong(String sql)
    {
        return this.getJdbcTemplate().queryForObject(sql, Long.class);
    }

    /**
     * Map类型列表查询
     *
     * @param sql
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql)
    {
        return this.getJdbcTemplate().queryForList(sql);
    }

    /**
     * Map类型列表查询
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String, Object>> queryForList(String sql, Object[] params)
    {
        return this.getJdbcTemplate().queryForList(sql, params);
    }
 
    /**
     * javabean列表查询 javabean的类型由rowMapper决定
     *
     * @param sql
     * @param params
     * @param rowMapper
     * @return
     */
    public <T> List<T> queryForList(String sql, Object[] params, RowMapper rowMapper)
    {
        return this.getJdbcTemplate().query(sql, params, rowMapper);
    }

    public <T> List<T> queryForList(String sql, Map<String, Object> map, RowMapper rowMapper)
    {
        return this.getNamedParameterJdbcTemplate().query(sql, map, rowMapper);
    }
    
    public List<Map<String, Object>> queryForList(String sql, Map<String, Object> map)
    {
        return this.getNamedParameterJdbcTemplate().queryForList(sql, map);
    }
    
    /**
     * 执行DDL语句(alter,create,drop ...)
     *
     * @param sql
     */
    public void execute(String sql)
    {
        this.getJdbcTemplate().execute(sql);
    }

    /**
     * @param sql
     * @param action
     */
    public Object execute(String sql, PreparedStatementCallback action)
    {
        return this.getJdbcTemplate().execute(sql, action);
    }
    
    public List<Map<String, Object>> queryForList(String table,String sql, Map<String, Object> map)
    {	List<Map<String,Object>> list = this.getNamedParameterJdbcTemplate().queryForList(sql, map);
     	if(!list.isEmpty()){
     		int totalNum = queryForInt(String.format("select count(id) from %s ", table));
        	list.get(0).put("totalNum", totalNum);
     	}
    	
    	return list;
    }
    
    /**
     * 获取分页sql语句
     *
     * @param sql
     * @param currentPage
     * @param pageSize
     * @return
     */
    public String getLimitSql(String sql, int currentPage, int pageSize)
    {
        Assert.isTrue(currentPage > 0, "'currentPage' 不能为负数!");
        Assert.isTrue(pageSize > 0, "'pageSize' 不能为负数!");
        String sqlExt=sql +String.format( " limit %s,%s ",((currentPage - 1) * pageSize), pageSize);
        return sqlExt;
    }

    /**
     * 获取总记录数sql语句
     *
     * @param sql
     * @return
     */
    public String getCountSql(String sql)
    {
        return String.format("select count(1) from (%s) a", sql);
    }

    public TransactionTemplate getTransactionTemplate() {
        return transactionTemplate;
    }

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

}
