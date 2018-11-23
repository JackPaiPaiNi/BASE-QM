
package com.skyworth.demo.echarts.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skyworth.core.base.BaseController;


/**
 * echarts图表 Controller
 * @author 王歌
 * 
 * */
@Controller
@RequestMapping("/demo/echarts")
public class EchartsController extends BaseController {
	
	@RequestMapping(value = {"list",""})
	public String list() {
		return "demo/echarts/demo1";
	}
	
	@RequestMapping(value = "map")
	public String mapList() {
		return "demo/echarts/map-sky";
	}
	
	@RequestMapping(value="fxbb1")
	public String mapData1(){
		return "demo/echarts/fxbb1";
	}
	
	@RequestMapping(value="fxbb2")
	public String demo2(){
		return "demo/echarts/fxbb2";
	}
	
}

