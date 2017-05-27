package com.xiyi.mapper;

import com.xiyi.model.UserRole;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * UserRole 表数据库控制层接口
 *
 */
public interface UserRoleMapper extends AutoMapper<UserRole> {

    List<UserRole> selectByUserId(@Param("userId") Long userId);

    List<Long> selectRoleIdListByUserId(@Param("userId") Long userId);

}