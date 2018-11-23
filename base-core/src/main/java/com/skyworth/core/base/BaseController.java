package com.skyworth.core.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class BaseController {

	protected final Logger logger = Logger.getLogger(getClass());

	/*
	 * 因为token出异常时不应该从session中删除，为了解决这个问题，所以方法移到HandlerMethodExceptionResolver中
	@ExceptionHandler
	public ModelAndView exp(HttpServletRequest request, HttpServletResponse response, Object handlerMethod, Exception ex) {
		
		String errorMsg = null;
		if (ex instanceof ValidateException) {
			errorMsg = ex.getMessage();
		} else if (ex instanceof ConstraintViolationException) {
			List<String> extractMessage = BeanValidators.extractMessage((ConstraintViolationException)ex);
			errorMsg = StringUtils.join(extractMessage, System.lineSeparator());
		} else if (ex instanceof ServiceException) {
			errorMsg = ex.getMessage();
			
			// 记录日志
			logger.error(ex.getMessage(), ex);
		} else {
			errorMsg = ExceptionUtils.getRootCauseMessage(ex);
			
			// 记录日志
			logger.error(ex.getMessage(), ex);
		}

		if ((request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			try {
				String msg = "{success:false,message:\""+errorMsg+"\"}";
				PrintWriter writer = response.getWriter();
				response.setCharacterEncoding("UTF-8");
				writer.write(msg);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		String resultViewName = null;
		
		Map<String, Object> model = new HashMap<String, Object>();//相当于使用request.setAttribute
		model.put("ex", ex);
		
		request.setAttribute("ex", ex);
		request.setAttribute("exTrace", ExceptionUtils.getStackTrace(ex));

		// 根据不同错误转向不同页面
		if (ex instanceof UnauthorizedException) {
			resultViewName = "errors/403";
		} else if (ex instanceof NoHandlerFoundException) {
			resultViewName = "errors/404";
		} else {
			resultViewName = "errors/500";
		}

		return new ModelAndView(resultViewName,model);
		return new ModelAndView(resultViewName);
	} */

	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
