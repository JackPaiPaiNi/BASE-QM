package com.skyworth.bi.pub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skyworth.bi.pub.service.PubService;
import com.skyworth.bi.pub.vo.PubVo;
import com.skyworth.core.base.BaseController;



/**
 * 公共加载数据Controller
 * @author 邓海
 */
@Controller
@RequestMapping(value = "bi/pub")
public class PubController extends BaseController {

	@Autowired
	private PubService pubService;

	/**
	 * 加载所有部门
	 * @return
	 */
	@RequestMapping(value = "findBmAll")
	@ResponseBody
	public Object findBmAll() {
		return pubService.findBmAll();
	}
	
	/**
	 * 加载部门（缓存）
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "findBmByGh")
	@ResponseBody
	public Object findBmByGh(PubVo vo) {
		vo.setBmjcdm(2);// 取分公司+办事处
		return pubService.findBmByGh(vo);
	}

	
}