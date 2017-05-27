package com.xiyi.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xiyi.model.Role;
import com.xiyi.model.SysLog;

/**
 *
 * SysLog 表数据库控制层接口
 *
 */
public interface SysLogMapper extends AutoMapper<SysLog> {

    List<Role> selectSysLogList(Pagination page);

}