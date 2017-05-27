package com.xiyi.service;

import com.xiyi.commons.utils.PageInfo;
import com.xiyi.model.Role;

import java.util.List;
import java.util.Map;

import com.baomidou.framework.service.ISuperService;

/**
 *
 * Role 表数据服务层接口
 *
 */
public interface IRoleService extends ISuperService<Role> {

    List<Long> selectRoleIdListByUserId(Long userId);

    List<Map<Long, String>> selectRoleResourceListByRoleId(Long roleId);

    void selectDataGrid(PageInfo pageInfo);

    Object selectTree();

    List<Long> selectResourceIdListByRoleId(Long id);

    void updateRoleResource(Long id, String resourceIds);

}