package com.xiyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiyi.commons.result.Tree;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.mapper.RoleMapper;
import com.xiyi.mapper.RoleResourceMapper;
import com.xiyi.mapper.UserRoleMapper;
import com.xiyi.model.Role;
import com.xiyi.model.RoleResource;
import com.xiyi.service.IRoleService;

/**
 *
 * Role 表数据服务层接口实现类
 *
 */
@Service
public class RoleServiceImpl extends SuperServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private RoleResourceMapper roleResourceMapper;
    
    @Override
    public List<Long> selectRoleIdListByUserId(Long userId) {
        return userRoleMapper.selectRoleIdListByUserId(userId);
    }

    @Override
    public List<Map<Long, String>> selectRoleResourceListByRoleId(Long roleId) {
        return roleMapper.selectResourceListByRoleId(roleId);
    }

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<Role> page = new Page<Role>(pageInfo.getNowpage(), pageInfo.getSize());
        List<Role> list = roleMapper.selectRoleList(page, pageInfo.getSort(), pageInfo.getOrder());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public Object selectTree() {
        List<Tree> trees = new ArrayList<Tree>();
        List<Role> roles = roleMapper.selectAll();
        for (Role role : roles) {
            Tree tree = new Tree();
            tree.setId(role.getId());
            tree.setText(role.getName());

            trees.add(tree);
        }
        return trees;
    }

    @Override
    public List<Long> selectResourceIdListByRoleId(Long id) {
        return roleMapper.selectResourceIdListByRoleId(id);
    }

    @Override
    public void updateRoleResource(Long roleId, String resourceIds) {
        // 先删除后添加,有点爆力
        RoleResource roleResource = new RoleResource();
        roleResource.setRoleId(roleId);
        roleResourceMapper.deleteSelective(roleResource);
        
        String[] resourceIdArray = resourceIds.split(",");
        for (String resourceId : resourceIdArray) {
            roleResource = new RoleResource();
            roleResource.setRoleId(roleId);
            roleResource.setResourceId(Long.parseLong(resourceId));
            roleResourceMapper.insert(roleResource);
        }
    }

}