package com.skyworth.core.login.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyworth.core.actor.vo.ActorPermissionVo;
import com.skyworth.core.login.dao.LoginDao;
import com.skyworth.core.menu.vo.MenuVo;
import com.skyworth.core.user.vo.UserVo;

/**
 * 登录管理Service
 * @author 魏诚
 */
@Service
public class LoginService {
	
	@Autowired
	private LoginDao dao;
	
	/**
	 * 根据工号取用户信息
	 * @param gh
	 * @return
	 */
	public UserVo findUserByGh(String gh) {
		if (StringUtils.isBlank(gh)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gh", gh);
		dao.selectUserByGh(param);
		@SuppressWarnings("unchecked")
		List<UserVo> list = (List<UserVo>) param.get("list");
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 根据工号、岗位取菜单
	 * @param gh工号
	 * @param gwid岗位ID
	 * @return
	 */
	public List<MenuVo> findCd(String gh){
		if (StringUtils.isBlank(gh)) {
			return null;
		}
		return this.getMenu(gh, 0);
	}
	
	/**
	 * 递归取菜单树
	 * @param gh
	 * @param gwid
	 * @param sjcdid
	 * @return
	 */
	private List<MenuVo> getMenu(String gh, Integer sjcdid){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gh", gh);
		param.put("sjcdid", sjcdid);
		dao.selectCd(param);
		@SuppressWarnings("unchecked")
		List<MenuVo> listMenu = (List<MenuVo>) param.get("list");
		// 转换菜单树
		List<MenuVo> myMenuList = new ArrayList<MenuVo>();
		for (int i = 0; i < listMenu.size(); i++) {
			MenuVo menuVo = listMenu.get(i);
			// 判断是否末级
			if (menuVo.getSfmj() != 1) {
				List<MenuVo> child = this.getMenu(gh, Integer.parseInt(menuVo.getId()));
				menuVo.setChildren(child);
			}
			myMenuList.add(menuVo);
		}
		return myMenuList;
	}
	
	/**
	 * 根据工号、岗位取权限
	 * @param gh工号
	 * @param gwid岗位ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActorPermissionVo> findQx(String gh){
		if (StringUtils.isBlank(gh)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gh", gh);
		dao.selectQx(param);
		return (List<ActorPermissionVo>) param.get("list");
	}
	
	/**
	 * 初始化session内容查询
	 * @param gh工号
	 * @param gwbm岗位编码
	 * @return
	 */
	public Map<String, Object> findSession(String gh){
		if (StringUtils.isBlank(gh)) {
			return null;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("gh", gh);
		dao.selectSession(param);
		return param;
	}
	
	/**
	 * 获取当前有用的时间格式
	 * @return
	 */
	public Map<String, String> getCurrentDate(){
		Map<String, String> result = new HashMap<String, String>();
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyyMM");
		SimpleDateFormat format4 = new SimpleDateFormat("yyyyMMdd");
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		// 当前日期
		String dqrq = format1.format(today);
		result.put("dqrq", dqrq);
		// 昨天
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String ztrq = format1.format(cal.getTime());
		result.put("ztrq", ztrq);
		// 当前年月
		String dqny = format2.format(today);
		result.put("dqny", dqny);
		// 当前年月(yyyymm)
		String yyyymm = format3.format(today);
		result.put("yyyymm", yyyymm);
		// 当前年月日(yyyymmdd)
		String yyyymmdd = format4.format(today);
		result.put("yyyymmdd", yyyymmdd);
		// 当月月初
		String dyyc = format2.format(today) + "-01";
		result.put("dyyc", dyyc);
		// 当月月底
		String dyyd = format2.format(today) + "-" + String.valueOf(maxDay);
		result.put("dyyd", dyyd);
		// 当前财年
		String dqcn = "";
		if(month > 3){
			dqcn = String.valueOf(year);
		} else {
			dqcn = String.valueOf(year - 1);
		}
		result.put("dqcn", dqcn);
		// 当前财年开始
		String dqcnks = dqcn + "-04";
		result.put("dqcnks", dqcnks);
		// 当前财年结束
		String dqcnjs = String.valueOf(Integer.valueOf(dqcn) + 1) + "-03";
		result.put("dqcnjs", dqcnjs);
		// 当前自然年开始
		String dqzrnks = String.valueOf(year) + "-01";
		result.put("dqzrnks", dqzrnks);
		// 当前自然年结束
		String dqzrnjs = String.valueOf(year) + "-12";
		result.put("dqzrnjs", dqzrnjs);
		
		return result;
	}
	
}