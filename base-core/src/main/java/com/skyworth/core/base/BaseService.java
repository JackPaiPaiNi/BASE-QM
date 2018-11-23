package com.skyworth.core.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyworth.core.base.entity.Page;
import com.skyworth.core.base.entity.Paginator;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.exception.ServiceException;
import com.skyworth.core.utils.ExcelExportUtils;
import com.skyworth.core.utils.UserUtils;

public abstract class BaseService<D extends BaseDao, T extends BaseEntity> {
	
	protected final Logger logger = Logger.getLogger(getClass());
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	@Autowired
	private ExcelExportUtils exportUtils;
	
	protected ResultEntity entity;
	
	public T findById(String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		dao.selectById(param);
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) param.get("list");
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	public Paginator queryByPage(T vo, Page page) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 数据权限过滤
		vo.preQuery();
		param.put("bmids", vo.getBmid());
		param.put("gh", UserUtils.getUser().getGh());
		
		param.put("vo", vo);
		param.put("page", page);
		dao.select(param);
		@SuppressWarnings("unchecked")
		List<T> rows = (List<T>) param.get("list");
		int total = (int) param.get("total");
		
		Paginator paginator = new Paginator(total, page.getRows(), rows);
		return paginator;
	}
	
	public List<T> query(T vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 数据权限过滤
		vo.preQuery();
		param.put("bmids", vo.getBmid());
		param.put("gh", UserUtils.getUser().getGh());
		
		param.put("vo", vo);
		dao.select(param);
		@SuppressWarnings("unchecked")
		List<T> rows = (List<T>) param.get("list");
		return rows;
	}
	
	public ResultEntity save(T vo) {
		if(StringUtils.isBlank(vo.getId())){
			vo.preInsert();
		} else {
			vo.preUpdate();
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.save(param);
		afterCallProcedure(param);
		entity.setVo(vo);
		return entity;
	}

	public ResultEntity delete(T vo) {
		vo.preUpdate();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.delete(param);
		afterCallProcedure(param);
		return entity;
	}
	
	public String export(T vo, Map<String, Object> params) {
		List<T> list = this.query(vo);
		return exportUtils.exportSync(list, params);
	}
	
	protected void afterCallProcedure(Map<String, Object> map){
		String resultMsg = map.get("resultMsg")==null?"":map.get("resultMsg").toString();
		if (!"000000".equals(map.get("resultCode").toString())) {
			throw new ServiceException(resultMsg);
		} else {
			entity = new ResultEntity();
			entity.setResult(map.get("resultCode").toString());
			entity.setMessage(resultMsg);
		}
	}
	
}

