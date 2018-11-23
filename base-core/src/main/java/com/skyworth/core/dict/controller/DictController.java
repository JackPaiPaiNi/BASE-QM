package com.skyworth.core.dict.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;
import com.skyworth.core.dict.service.DictService;
import com.skyworth.core.dict.vo.DictVo;

/**
 * 数据字典管理Controller
 * @author 魏诚
 */
@Controller
@RequestMapping(value = "core/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;
	
	@RequiresPermissions("core:dict:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "core/dict";
	}
	
	@RequiresPermissions("core:dict:view")
    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(DictVo vo, Page page) {
        return dictService.queryByPage(vo, page);
    }
	
	@RequiresPermissions("core:dict:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return dictService.findById(id);
    }
    
	@RequiresPermissions("core:dict:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(DictVo vo) {
    	return dictService.save(vo);
    }
    
    @RequiresPermissions("core:dict:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Object delete(DictVo vo) {
		return dictService.delete(vo);
    }

}