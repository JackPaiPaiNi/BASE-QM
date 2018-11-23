
package com.skyworth.bi.mdm.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.bi.mdm.service.YhjzxxService;
import com.skyworth.bi.mdm.vo.YhjzxxVo;
import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;


/**
 * 用户兼职信息 Controller
 * @author 高文浩
 * 
 * */
@Controller
@RequestMapping("/mdm/yhjzxx")
public class YhjzxxController extends BaseController {
	@Autowired
	private YhjzxxService  yhjzxxService;
	
	@RequiresPermissions("mdm:yhjzxx:view")
	@RequestMapping(value = {"list",""})
	public String list() {
		return "bi/mdm/yhjzxxList";
	}
	
	@RequiresPermissions("mdm:yhjzxx:view")
	@RequestMapping(value = "search")
	@ResponseBody
	public Object search(YhjzxxVo vo, Page page) {
		return yhjzxxService.queryByPage(vo, page);
	}
	
	@RequiresPermissions("mdm:yhjzxx:view")
	@RequestMapping(value = "jzbmQuery")
	@ResponseBody
	public Object jzbmQuery(YhjzxxVo vo) {
		return yhjzxxService.jzbmQuery(vo);
	}
	
	@RequiresPermissions("mdm:yhjzxx:save")
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(YhjzxxVo vo) {
		return yhjzxxService.save(vo);
	}
	
	@RequiresPermissions("mdm:yhjzxx:save")
	@RequestMapping(value = "cancel")
	@ResponseBody
	public Object cancel(YhjzxxVo vo) {
		return yhjzxxService.cancel(vo);
	}
	
	
}

