package com.skyworth.core.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyworth.core.base.entity.Page;
import com.skyworth.core.base.entity.Paginator;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.exception.ServiceException;
import com.skyworth.core.user.dao.UserDao;
import com.skyworth.core.user.vo.UserVo;
import com.skyworth.core.utils.ExcelExportUtils;
import com.skyworth.core.utils.UserUtils;

/**
 * 用户信息管理Service
 * 
 * @author 魏诚
 */
@Service
public class UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private ExcelExportUtils exportUtils;
	/*
	 * @Autowired private PasswordHelper passwordHelper;
	 */

	public Paginator queryByPage(UserVo vo, Page page) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 数据权限过滤

		if (StringUtils.isBlank(vo.getBmid())) {
			param.put("bmids", UserUtils.getViewOrgs());
		} else {
			param.put("bmids", vo.getBmid());
		}
		param.put("gh", UserUtils.getUser().getGh());

		param.put("vo", vo);
		param.put("page", page);
		dao.select(param);
		@SuppressWarnings("unchecked")
		List<UserVo> rows = (List<UserVo>) param.get("list");
		int total = (int) param.get("total");

		Paginator paginator = new Paginator(total, page.getRows(), rows);
		return paginator;
	}

	public List<UserVo> query(UserVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		// 数据权限过滤
		if (StringUtils.isBlank(vo.getBmid())) {
			param.put("bmids", UserUtils.getViewOrgs());
		} else {
			param.put("bmids", vo.getBmid());
		}
		param.put("gh", UserUtils.getUser().getGh());

		param.put("vo", vo);
		dao.select(param);
		@SuppressWarnings("unchecked")
		List<UserVo> rows = (List<UserVo>) param.get("list");
		return rows;
	}

	public UserVo findByGh(String gh) {
		if (StringUtils.isBlank(gh)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gh", gh);
		dao.selectByGh(param);
		@SuppressWarnings("unchecked")
		List<UserVo> list = (List<UserVo>) param.get("list");
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * TaskListener根据岗位ID取用户信息和用户兼职信息
	 * 
	 * @param gwid
	 * @param bmid
	 * @param isSameOrg
	 * @param isSameGroup
	 * @return
	 */
	public List<UserVo> findByGw(Integer gwid, String bmid, boolean isSameOrg, boolean isSameGroup) {
		if (gwid == null) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gwid", gwid);
		param.put("bmid", bmid);
		if (isSameOrg) {
			param.put("bsc", 1);
		} else {
			param.put("bsc", 0);
		}
		if (isSameGroup) {
			param.put("fgs", 1);
		} else {
			param.put("fgs", 0);
		}
		dao.selectByGw(param);
		@SuppressWarnings("unchecked")
		List<UserVo> list = (List<UserVo>) param.get("list");
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 开启审批流时根据岗位ID判断用户信息和用户兼职信息是否存在
	 * 
	 * @param gwid
	 * @param bmid
	 * @param isSameOrg
	 * @param isSameGroup
	 * @return 0 1
	 */
	public String checkByGw(Integer gwid, String bmid, boolean isSameOrg, boolean isSameGroup) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gwid", gwid);
		param.put("bmid", bmid);
		if (isSameOrg) {
			param.put("bsc", 1);
		} else {
			param.put("bsc", 0);
		}
		if (isSameGroup) {
			param.put("fgs", 1);
		} else {
			param.put("fgs", 0);
		}
		dao.checkByGw(param);
		return param.get("isExists").toString();
	}

	public ResultEntity save(UserVo vo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("vo", vo);
		dao.save(param);
		if (!"000000".equals(param.get("resultCode").toString())) {
			throw new ServiceException(param.get("resultMsg").toString());
		} else {
			ResultEntity entity = new ResultEntity();
			entity.setResult(param.get("resultCode").toString());
			entity.setMessage(param.get("resultMsg").toString());
			entity.setVo(vo);
			return entity;
		}
	}

	public String export(UserVo vo, Map<String, Object> params) {
		List<UserVo> list = this.query(vo);
		return exportUtils.exportSync(list, params);
	}

}