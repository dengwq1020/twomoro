package com.xiyi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyi.commons.base.BaseController;
import com.xiyi.model.Resource;
import com.xiyi.model.User;
import com.xiyi.service.IResourceService;

/**
 * @description：资源管理
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Autowired
    private IResourceService resourceService;

    /**
     * 菜单树
     *
     * @return
     */
    @RequestMapping(value="/tree")
    //@PostMapping("/tree")
    @ResponseBody
    public Object tree() {
        User currentUser = getCurrentUser();
        return resourceService.selectTree(currentUser);
    }

    /**
     * 资源管理页
     *
     * @return
     */
    @RequestMapping(value="/manager", method = RequestMethod.GET)
    //@GetMapping("/manager")
    public String manager() {
        return "admin/resource";
    }

    /**
     * 资源管理列表
     *
     * @return
     */
    @RequestMapping(value="/treeGrid", method = RequestMethod.POST)
    //@PostMapping("/treeGrid")
    @ResponseBody
    public Object treeGrid() {
        return resourceService.selectAll();
    }

    /**
     * 添加资源页
     *
     * @return
     */
    @RequestMapping(value="/addPage", method = RequestMethod.GET)
    //@GetMapping("/addPage")
    public String addPage() {
        return "admin/resourceAdd";
    }

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(Resource resource) {
        resourceService.insert(resource);
        return renderSuccess("添加成功！");
    }

    /**
     * 二级资源树
     *
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public Object allTree() {
        return resourceService.selectAllTree();
    }

    /**
     * 三级资源树
     *
     * @return
     */
    @RequestMapping(value="/allTrees")
    //@PostMapping("/allTrees")
    @ResponseBody
    public Object allTrees() {
        return resourceService.selectAllTrees();
    }

    /**
     * 编辑资源页
     *
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editPage")
    public String editPage(Model model, Long id) {
        Resource resource = resourceService.selectById(id);
        model.addAttribute("resource", resource);
        return "admin/resourceEdit";
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public Object edit(Resource resource) {
        resourceService.updateSelectiveById(resource);
        return renderSuccess("编辑成功！");
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(Long id) {
        resourceService.deleteById(id);
        return renderSuccess("删除成功！");
    }

}
