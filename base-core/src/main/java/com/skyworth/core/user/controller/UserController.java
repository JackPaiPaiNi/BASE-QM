package com.skyworth.core.user.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;
import com.skyworth.core.user.service.UserService;
import com.skyworth.core.user.vo.UserVo;

/**
 * 用户信息管理Controller
 * 
 * @author 魏诚
 */
@Controller
@RequestMapping(value = "core/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	@RequiresPermissions("core:user:view")
	@RequestMapping(value = { "list", "" })
	public String list() {
		return "core/user";
	}

	@RequiresPermissions("core:user:view")
	@RequestMapping(value = "search")
	@ResponseBody
	public Object search(UserVo vo, Page page) {
		return userService.queryByPage(vo, page);
	}

	@RequiresPermissions("core:user:view")
	@RequestMapping(value = "findByGh")
	@ResponseBody
	public Object findByGh(String gh) {
		return userService.findByGh(gh);
	}

	@RequiresPermissions("core:user:save")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(UserVo vo) {
		return userService.save(vo);
	}

	@RequestMapping(value = "export")
	@ResponseBody
	public String export(UserVo vo, @RequestParam Map<String, Object> params) {
		return userService.export(vo, params);
	}

	@RequiresPermissions("core:user:view")
	@RequestMapping(value = "query")
	@ResponseBody
	public Object query(UserVo vo) {
		return userService.query(vo);
	}

}