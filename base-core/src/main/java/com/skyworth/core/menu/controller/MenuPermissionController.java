package com.skyworth.core.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;
import com.skyworth.core.menu.service.MenuPermissionService;
import com.skyworth.core.menu.vo.MenuPermissionVo;

/**
 * 菜单权限信息管理Controller
 * @author 魏诚
 */
@Controller
@RequestMapping(value = "menu/permission")
public class MenuPermissionController extends BaseController {

	@Autowired
	private MenuPermissionService menuPermissionService;
	
	@RequiresPermissions("core:menu:view")
    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(MenuPermissionVo vo, Page page) {
        return menuPermissionService.queryByPage(vo, page);
    }
	
	@RequiresPermissions("core:menu:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return menuPermissionService.findById(id);
    }
    
    @RequiresPermissions("core:menu:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(MenuPermissionVo vo) {
		return menuPermissionService.save(vo);
    }
    
    @RequiresPermissions("core:menu:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Object delete(MenuPermissionVo vo) {
		return menuPermissionService.delete(vo);
    }

}