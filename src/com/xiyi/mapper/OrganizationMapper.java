package com.xiyi.mapper;

import com.xiyi.model.Organization;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.AutoMapper;

/**
 *
 * Organization 表数据库控制层接口
 *
 */
public interface OrganizationMapper extends AutoMapper<Organization> {

    List<Organization> selectByPIdNull();

    List<Organization> selectAllByPId(@Param("pId") Long pid);

    List<Organization> selectAll();

}