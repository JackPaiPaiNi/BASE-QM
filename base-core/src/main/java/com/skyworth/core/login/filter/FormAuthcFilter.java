package com.skyworth.core.login.filter;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.skyworth.core.actor.vo.ActorVo;
import com.skyworth.core.common.Constants;
import com.skyworth.core.login.service.LoginService;
import com.skyworth.core.user.vo.UserVo;
import com.skyworth.core.utils.AjaxUtils;

/**
 * 登陆拦截
 * 
 * @author 王歌
 *
 */
public class FormAuthcFilter extends FormAuthenticationFilter {
	
	@Autowired
    private LoginService loginService;

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		Session session = subject.getSession();
		String gh = (String) subject.getPrincipal();
		UserVo userVo = loginService.findUserByGh(gh);
		session.setAttribute(Constants.CURRENT_USER, userVo);
		
		Map<String, Object> initDataMap = loginService.findSession(gh);
		
		@SuppressWarnings("unchecked")
		List<ActorVo> actors = (List<ActorVo>) initDataMap.get("actorList");
		session.setAttribute(Constants.CURRENT_ACTORS, actors);
		
		String view_orgs = (String) initDataMap.get("viewOrgList");
		session.setAttribute(Constants.CURRENT_VIEW_ORGS, view_orgs);
		
		String oper_orgs = (String) initDataMap.get("operOrgList");
		session.setAttribute(Constants.CURRENT_OPER_ORGS, oper_orgs);
		
		Map<String, String> current_date = loginService.getCurrentDate();
		session.setAttribute(Constants.CURRENT_DATE, current_date);
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		String url = getSuccessUrl();
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + url);	//页面跳转
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else if (AjaxUtils.isAjaxRequest(WebUtils.toHttp(request))) {
				HttpServletResponse res = WebUtils.toHttp(response);
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			return true;
		}
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}

	/**
	 * 重写取密码参数方法，直接取前台传过来的参数，原方法会去除首尾空格
	 */
	@Override
    protected String getPassword(ServletRequest request) {
        return request.getParameter(getPasswordParam());
    }

}