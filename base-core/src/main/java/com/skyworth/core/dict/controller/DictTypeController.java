package com.skyworth.core.dict.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.dict.service.DictTypeService;
import com.skyworth.core.dict.vo.DictTypeVo;

/**
 * 字典信息管理Controller
 * @author tqq
 */
@Controller
@RequestMapping(value = "dict/dictType")
public class DictTypeController extends BaseController {

	@Autowired
	private DictTypeService dictTypeService;
	
	@RequiresPermissions("core:dict:view")
    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(DictTypeVo vo, Page page) {
        return dictTypeService.queryByPage(vo, page);
    }
	
	@RequiresPermissions("core:dict:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return dictTypeService.findById(id);
    }
    
    @RequiresPermissions("core:dict:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public ResultEntity save(DictTypeVo vo) {
    	return dictTypeService.save(vo);
    }
    
    @RequiresPermissions("core:dict:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public ResultEntity delete(DictTypeVo vo) {
		return dictTypeService.delete(vo);
    }

}