package com.skyworth.bi.mdm.controller;

import java.util.Map;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.bi.mdm.service.ZwgwService;
import com.skyworth.bi.mdm.vo.ZwgwVo;
import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;

/**
 * 职务岗位对应关系管理DAO接口
 * 
 * @author tianrong
 */

@Controller
@RequestMapping(value = "mdm/zwgw")
public class ZwgwController extends BaseController {
	
	@Autowired
	private ZwgwService zwgwservice;
	
	@RequiresPermissions("mdm:zwgw:view")
	@RequestMapping(value = {"list",""})
	public String list() {
		return "bi/mdm/zwgwList";
	}
	
	@RequiresPermissions("mdm:zwgw:view")
	@RequestMapping(value = "search")
	@ResponseBody
	public Object search(ZwgwVo vo, Page page) {
		return zwgwservice.queryByPage(vo, page);
	}
	
	@RequiresPermissions("mdm:zwgw:save")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(ZwgwVo vo) {
		return zwgwservice.save(vo);
	}
	
	@RequiresPermissions("mdm:zwgw:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(ZwgwVo vo) {
		return zwgwservice.delete(vo);
	}
	
	@RequiresPermissions("mdm:zwgw:view")
    @RequestMapping(value = "export")
    @ResponseBody
    public String export(ZwgwVo vo, @RequestParam Map<String, Object> params) {
    	return zwgwservice.export(vo, params);
    }
}