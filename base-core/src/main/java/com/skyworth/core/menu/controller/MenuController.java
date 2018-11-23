package com.skyworth.core.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;
import com.skyworth.core.menu.service.MenuService;
import com.skyworth.core.menu.vo.MenuVo;

/**
 * 菜单信息管理Controller
 * @author 魏诚
 */
@Controller
@RequestMapping(value = "core/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;
	
	@RequiresPermissions("core:menu:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "core/menu";
	}
	
	@RequiresPermissions("core:menu:view")
    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(MenuVo vo, Page page) {
        return menuService.queryByPage(vo, page);
    }
	
	@RequiresPermissions("core:menu:view")
    @RequestMapping(value = "query")
    @ResponseBody
    public Object query(MenuVo vo) {
        return menuService.query(vo);
    }
	
	@RequiresPermissions("core:menu:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return menuService.findById(id);
    }
    
    @RequiresPermissions("core:menu:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(MenuVo vo) {
		return menuService.save(vo);
    }
    
    @RequiresPermissions("core:menu:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Object delete(MenuVo vo) {
		return menuService.delete(vo);
    }

}