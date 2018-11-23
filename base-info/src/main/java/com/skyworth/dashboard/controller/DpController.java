package com.skyworth.dashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.core.base.BaseController;
import com.skyworth.dashboard.service.DpService;
import com.skyworth.dashboard.vo.DpVo;

/**
 * Description:驾驶舱图表Controller
 * 
 * @author 魏诚
 * @date 2018年8月21日
 */
@Controller
@RequestMapping("/dashboard/dp")
public class DpController extends BaseController {

	@Autowired
	private DpService dpService;
	
	@RequestMapping(value = "yxzb")
	public String yxzbPage() {
		return "dashboard/yxzb";
	}

	@RequestMapping(value = "fgs")
	public String fgsPage() {
		return "dashboard/fgs";
	}
	
	@RequestMapping(value = "searchYwbm")
	@ResponseBody
	public Object searchYwbm(DpVo vo) {
		return dpService.queryYwbm(vo);
	}
	
	@RequestMapping(value = "searchFgs")
	@ResponseBody
	public Object searchFgs(DpVo vo) {
		return dpService.queryFgs(vo);
	}

	@RequestMapping(value = "searchMap")
	@ResponseBody
	public Object searchMap(DpVo vo) {
		return dpService.queryMap(vo);
	}
	
}
