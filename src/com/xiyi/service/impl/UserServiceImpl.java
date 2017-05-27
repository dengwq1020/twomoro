package com.xiyi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.framework.service.impl.SuperServiceImpl;
import com.baomidou.mybatisplus.plugins.Page;
import com.xiyi.commons.utils.BeanUtils;
import com.xiyi.commons.utils.PageInfo;
import com.xiyi.mapper.UserMapper;
import com.xiyi.mapper.UserRoleMapper;
import com.xiyi.model.User;
import com.xiyi.model.UserRole;
import com.xiyi.model.vo.UserVo;
import com.xiyi.service.IUserService;

/**
 *
 * User 表数据服务层接口实现类
 *
 */
@Service
public class UserServiceImpl extends SuperServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public User selectByLoginName(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        return this.selectOne(user);
    }

    @Override
    public void insertByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        this.insert(user);
        
        Long id = user.getId();
        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();

        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public UserVo selectVoById(Long id) {
        return userMapper.selectUserVoById(id);
    }

    @Override
    public void updateByVo(UserVo userVo) {
        User user = BeanUtils.copy(userVo, User.class);
        this.updateSelectiveById(user);
        
        Long id = userVo.getId();
        List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }

        String[] roles = userVo.getRoleIds().split(",");
        UserRole userRole = new UserRole();
        for (String string : roles) {
            userRole.setUserId(id);
            userRole.setRoleId(Long.valueOf(string));
            userRoleMapper.insert(userRole);
        }
    }

    @Override
    public void updatePwdByUserId(Long userId, String md5Hex) {
        User user = new User();
        user.setPassword(md5Hex);
        this.updateSelectiveById(user);
    }

    @Override
    public void selectDataGrid(PageInfo pageInfo) {
        Page<UserVo> page = new Page<UserVo>(pageInfo.getNowpage(), pageInfo.getSize());
        List<UserVo> list = userMapper.selectUserVoPage(page, pageInfo.getCondition());
        pageInfo.setRows(list);
        pageInfo.setTotal(page.getTotal());
    }

    @Override
    public void deleteUserById(Long id) {
        this.deleteById(id);
        List<UserRole> userRoles = userRoleMapper.selectByUserId(id);
        if (userRoles != null && !userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                userRoleMapper.deleteById(userRole.getId());
            }
        }
    }

}