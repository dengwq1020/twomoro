package com.xiyi.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.xiyi.model.Resource;
import com.xiyi.model.Role;

/**
 *
 * Role 表数据库控制层接口
 *
 */
public interface RoleMapper extends AutoMapper<Role> {

    List<Role> selectAll();

    List<Long> selectResourceIdListByRoleId(@Param("id") Long id);

    List<Resource> selectResourceIdListByRoleIdAndType(@Param("id") Long id);

    List<Map<Long, String>> selectResourceListByRoleId(@Param("id") Long id);

    List<Role> selectRoleList(Pagination page, @Param("sort") String sort, @Param("order") String order);

}