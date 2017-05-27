package com.xiyi.service;

import java.util.List;

import com.baomidou.framework.service.ISuperService;
import com.xiyi.commons.result.Tree;
import com.xiyi.model.Resource;
import com.xiyi.model.User;

/**
 *
 * Resource 表数据服务层接口
 *
 */
public interface IResourceService extends ISuperService<Resource> {

    List<Resource> selectAll();

    List<Tree> selectAllTree();

    List<Tree> selectAllTrees();

    List<Tree> selectTree(User currentUser);

}