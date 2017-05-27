package com.xiyi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyi.commons.base.BaseController;
import com.xiyi.commons.scan.SpringUtils;
import com.xiyi.commons.utils.JsonUtils;
import com.xiyi.commons.utils.StringUtils;
import com.xiyi.event.WebHooksEvent;

/**
 * WebHooks 自动更新部署
 * Created by L.cm on 2016/9/23.
 */
@Controller
@SuppressWarnings("unchecked")
public class WebHooksController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WebHooksController.class);
    
    /**
     * git@osc WebHooks 设置
     * WIKI: http://git.oschina.net/oschina/git-osc/wikis/WebHook-%E4%BD%BF%E7%94%A8%E7%AE%80%E4%BB%8B
     * @param hook json字符串
     * @return jsonBean
     */
    @RequestMapping(value="/webhooks", method = RequestMethod.POST)
    //@PostMapping("webhooks")
    @ResponseBody
    public Object hooks(HttpServletRequest request) {
        String hook = request.getParameter("hook");
        if (StringUtils.isBlank(hook)) {
            return renderError("json hook isBlank!");
        }
        logger.info("webhooks json: {}", hook);
        Map<String, Object> hookMap = JsonUtils.parse(hook, Map.class);
        // 发送事件 ThreadPoolTaskExecutor
        SpringUtils.publishEvent(new WebHooksEvent(hookMap));
        return renderSuccess("ok");
    }
}
