package com.xiyi.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyi.commons.base.BaseController;
import com.xiyi.commons.csrf.CsrfToken;
import com.xiyi.commons.utils.DigestUtils;
import com.xiyi.commons.utils.StringUtils;

/**
 * @description：登录退出
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@Controller
public class LoginController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    /**
     * 首页
     *
     * @return
     */
    @RequestMapping(value="/", method = RequestMethod.GET)
    //@GetMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    //@GetMapping("/index")
    @RequestMapping(value="/index", method = RequestMethod.GET)
    public String index(Model model) {
        return "index";
    }

    /**
     * GET 登录
     * @return {String}
     */
    @RequestMapping(value="/login", method = RequestMethod.GET)
    //@GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        LOGGER.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @RequestMapping(value="/login", method = RequestMethod.POST)
    //@PostMapping("/login")
    @CsrfToken(remove = true)
    @ResponseBody
    public Object loginPost(String username, String password) {
        LOGGER.info("POST请求登录");

        if (StringUtils.isBlank(username)) {
            return renderError("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return renderError("密码不能为空");
        }
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
        // 默认设置为记住密码，你可以自己在表单中加一个参数来控制
        LOGGER.info(""+DigestUtils.md5Hex(password));
        token.setRememberMe(true);
        try {
            user.login(token);
        } catch (UnknownAccountException e) {
            LOGGER.error("账号不存在！", e);
            return renderError("账号不存在");
        } catch (DisabledAccountException e) {
            LOGGER.error("账号未启用！", e);
            return renderError("账号未启用");
        } catch (IncorrectCredentialsException e) {
            LOGGER.error("密码错误！", e);
            return renderError("密码错误");
        } catch (RuntimeException e) {
            LOGGER.error("未知错误,请联系管理员！", e);
            return renderError("未知错误,请联系管理员");
        }
        return renderSuccess();
    }

    /**
     * 未授权
     * @return {String}
     */
    @RequestMapping(value="/unauth", method = RequestMethod.GET)
    //@GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     * @return {Result}
     */
    @RequestMapping(value="/logout", method = RequestMethod.POST)
    //@PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        LOGGER.info("登出");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }

}
