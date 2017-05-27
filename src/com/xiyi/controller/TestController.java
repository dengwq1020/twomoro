package com.xiyi.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiyi.commons.base.BaseController;

/**
 * @description：测试Controller
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

    /**
     * 图标测试
     * @return
     */
	@RequestMapping(value="/dataGrid", method = RequestMethod.GET)
    //@GetMapping("/dataGrid")
    public String dataGrid() {
        return "admin/test";
    }

}
