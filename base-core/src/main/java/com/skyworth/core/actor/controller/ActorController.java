package com.skyworth.core.actor.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.skyworth.core.actor.service.ActorService;
import com.skyworth.core.actor.vo.ActorVo;
import com.skyworth.core.base.BaseController;
import com.skyworth.core.base.entity.Page;

/**
 * 岗位信息管理Controller
 * @author 魏诚
 */
@Controller
@RequestMapping(value = "core/actor")
public class ActorController extends BaseController {

	@Autowired
	private ActorService actorService;

	@RequiresPermissions("core:actor:view")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "core/actor";
	}
	
	@RequiresPermissions("core:actor:view")
    @RequestMapping(value = "search")
    @ResponseBody
    public Object search(ActorVo vo, Page page) {
        return actorService.queryByPage(vo, page);
    }
	
	@RequiresPermissions("core:actor:view")
    @RequestMapping(value = "query")
    @ResponseBody
    public Object query(ActorVo vo) {
        return actorService.query(vo);
    }
	
	@RequiresPermissions("core:actor:view")
    @RequestMapping(value = "findMenuQx")
    @ResponseBody
    public Object findMenuQx(Integer gwid) {
        return actorService.queryMenuQx(gwid);
    }
	
	@RequiresPermissions("core:actor:view")
    @RequestMapping(value = "findById")
    @ResponseBody
    public Object findById(String id) {
        return actorService.findById(id);
    }
	
    @RequiresPermissions("core:actor:save")
    @RequestMapping(value = "save")
    @ResponseBody
    public Object save(ActorVo vo) {
		return actorService.save(vo);
    }
    
    @RequiresPermissions("core:actor:delete")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Object delete(ActorVo vo) {
		return actorService.delete(vo);
    }
    
    @RequiresPermissions("core:actor:save")
    @RequestMapping(value = "saveQx")
    @ResponseBody
    public Object saveQx(Integer gwid, String cdid, String qxid) {
		return actorService.saveQx(gwid, cdid, qxid);
    }
    
}