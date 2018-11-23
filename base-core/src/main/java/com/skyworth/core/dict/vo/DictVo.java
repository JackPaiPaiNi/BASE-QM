package com.skyworth.core.dict.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skyworth.core.dict.entity.Dict;

/**
 * 数据字典管理Entity
 * @author 魏诚
 */
@JsonIgnoreProperties({ "oper", "zdrid", "zdr", "zdsj", "sjc", 
	"token", "processId", "taskId", "approveType", "spyj", "inportMsg", "bmid" })
public class DictVo extends Dict {


	public DictVo() {
		super();
	}

	public DictVo(String id){
		super(id);
	}

}