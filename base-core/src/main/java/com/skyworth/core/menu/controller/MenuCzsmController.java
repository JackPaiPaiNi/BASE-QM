package com.skyworth.core.menu.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.menu.service.MenuCzsmService;
import com.skyworth.core.menu.service.MenuService;
import com.skyworth.core.menu.vo.MenuCzsmVo;
import com.skyworth.core.menu.vo.MenuVo;

/**
 * 菜单信息管理Controller
 * @author 王歌
 */
@Controller
@RequestMapping(value = "core/menuCzsm")
public class MenuCzsmController extends BaseController {

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuCzsmService menuCzsmService;
	
	@RequiresPermissions("core:menuCzsm:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "core/menuCzsm";
	}
	
	@RequiresPermissions("core:menuCzsm:view")
    @RequestMapping(value = "query")
    @ResponseBody
    public Object query(MenuVo vo) {
		vo.setIsXtcd(0);
        return menuService.query(vo);
    }
	
	@RequiresPermissions("core:menuCzsm:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return menuCzsmService.findById(id);
    }
    
    @RequiresPermissions("core:menuCzsm:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(MenuCzsmVo vo) {
		return menuCzsmService.save(vo);
    }

}