package com.skyworth.core.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.common.Constants;
import com.skyworth.core.token.annotation.Token;
import com.skyworth.core.utils.BeanValidators;

public class HandlerMethodExceptionResolver extends ExceptionHandlerExceptionResolver {

	@SuppressWarnings("unchecked")
	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception e) {
		
		if (handlerMethod != null) {
			Method method = handlerMethod.getMethod();
			if (method != null) {
				Token annotation = method.getAnnotation(Token.class);
				if (annotation != null) {
					boolean needSaveToken = annotation.verify();
					if (needSaveToken) {//出现异常token要再加回来
						String clinetToken = request.getParameter(Constants.CURRENT_TOKEN);
						Map<String, String> serverToken = ((Map<String, String>) request.getSession()
								.getAttribute(Constants.CURRENT_TOKEN));
						serverToken.put(clinetToken, "");
					}
				}
			}
		}
		
		// 取得错误信息
		String errorMsg = null;
		if (e instanceof ValidateException) {
			errorMsg = e.getMessage();
		} else if (e instanceof ConstraintViolationException) {
			List<String> extractMessage = BeanValidators.extractMessage((ConstraintViolationException) e);
			errorMsg = StringUtils.join(extractMessage, System.lineSeparator());
		} else if (e instanceof ServiceException) {
			errorMsg = e.getMessage();
			// 记录日志
			logger.error(e.getMessage(), e);
		} else {
			errorMsg = ExceptionUtils.getRootCauseMessage(e);
			// 记录日志
			logger.error(e.getMessage(), e);
		}
		
		// ajax请求处理
		if (request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null
				&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)) {
			try {
				String msg = "";
				response.setCharacterEncoding("UTF-8");
				if(!StringUtils.isBlank(StringUtils.isBlank(request.getParameter("_search"))?"":request.getParameter("_search"))){
					msg = "{success:false,message:\""+errorMsg+"\"}";
				} else {
					ResultEntity entity = new ResultEntity();
					entity.setResult("0");
					entity.setMessage(errorMsg);
					
					ObjectMapper mapper = new ObjectMapper();
					msg = mapper.writeValueAsString(entity);
				}
				PrintWriter writer = response.getWriter();
				writer.write(msg);
				writer.flush();
			} catch (IOException exception) {
				exception.printStackTrace();
			}
			return null;
		}

		// 根据不同错误转向不同页面
		String resultViewName = null;
		if (e instanceof UnauthorizedException) {
			resultViewName = "errors/403";
		} else if (e instanceof NoHandlerFoundException) {
			resultViewName = "errors/404";
		} else {
			resultViewName = "errors/500";
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("exTrace", ExceptionUtils.getStackTrace(e));
		result.put("errorMsg", errorMsg);
		return new ModelAndView(resultViewName, result);
	}
}
