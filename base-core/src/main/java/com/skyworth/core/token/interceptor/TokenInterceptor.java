package com.skyworth.core.token.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skyworth.core.common.Constants;
import com.skyworth.core.token.annotation.Token;

public class TokenInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Token annotation = method.getAnnotation(Token.class);
			if (annotation != null) {
				boolean needSaveToken = annotation.create();
				if (needSaveToken) {
					saveToken(request);
				}
				boolean needRemoveToken = annotation.verify();
				if (needRemoveToken) {
					if (isRepeatSubmit(request)) {
						sendAjaxError(request, response);
						return false;
					}
				}
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean isRepeatSubmit(HttpServletRequest request) {
		Map<String, String> serverToken = (Map<String, String>) request.getSession()
				.getAttribute(Constants.CURRENT_TOKEN);
		if (serverToken == null) {
			return true;
		}
		String clinetToken = request.getParameter(Constants.CURRENT_TOKEN);
		if (clinetToken == null) {
			return true;
		}
		if (!serverToken.containsKey(clinetToken)) {
			return true;
		}
		serverToken.remove(clinetToken);
		return false;
	}

	private void sendAjaxError(HttpServletRequest request, HttpServletResponse response) {
		if ((request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			try {
				String msg = "{success:false,message:\"重复提交或凭证过期,请刷新页面再操作\"}";
				PrintWriter writer = response.getWriter();
				response.setCharacterEncoding("UTF-8");
				writer.write(msg);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String saveToken(HttpServletRequest request) {
		Map<String, String> tokenMap = (Map<String, String>) request.getSession().getAttribute(Constants.CURRENT_TOKEN);
		if (tokenMap == null) {
			tokenMap = new HashMap<String, String>();
			request.getSession().setAttribute(Constants.CURRENT_TOKEN, tokenMap);
		}
		String token = UUID.randomUUID().toString();
		tokenMap.put(token, "");
		request.getSession().setAttribute(Constants.CURRENT_TOKEN, tokenMap);
		request.setAttribute(Constants.CURRENT_TOKEN, token);
		return token;
	}
}