package com.skyworth.core.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skyworth.core.login.service.LoginService;
import com.skyworth.core.menu.vo.MenuVo;
import com.skyworth.core.user.vo.UserVo;
import com.skyworth.core.utils.UserUtils;

/**
 * <p>User: 魏诚
 */
@Controller
public class LoginController {
	
	@Autowired
    private LoginService loginService;
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping("/")
    public String index(Model model) {
		UserVo user = UserUtils.getUser();
		List<MenuVo> menuList = loginService.findCd(user.getGh());
		model.addAttribute("menuList", menuList);
        return "index/main";
    }
	
	/**
	 * 登录页面
	 * @return
	 */
	@RequestMapping("/login")
    public String login(HttpServletRequest req, Model model) {
		String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
		if (exceptionClassName != null) {
			String error = null;
			if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
				error = "账号不存在";
			} else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
				error = "用户名/密码错误";
			} else if (DisabledAccountException.class.getName().equals(exceptionClassName)) {
				error = "账号被禁用";
			} else {
				error = "其他错误：" + exceptionClassName;
			}
			model.addAttribute("error", error);
		}
        return "login";
    }
	
}
