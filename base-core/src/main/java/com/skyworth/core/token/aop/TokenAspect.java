package com.skyworth.core.token.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import com.skyworth.core.base.BaseEntity;
import com.skyworth.core.base.entity.ResultEntity;
import com.skyworth.core.common.Constants;
import com.skyworth.core.token.annotation.Token;

/**
 * Token拦截器
 * 
 * @author 魏诚
 *
 */
@Aspect
@Order(1)
public class TokenAspect {

	@AfterReturning(pointcut = "@annotation(com.skyworth.core.token.annotation.Token)", returning = "returnValue")
	public void afterReturning(JoinPoint point, Object returnValue) {
		if (returnValue != null) {
			if (returnValue instanceof ResultEntity) {
				Signature signature = point.getSignature();
				MethodSignature methodSignature = (MethodSignature) signature;
				Method method = methodSignature.getMethod();

				if (method != null) {
					Token annotation = method.getAnnotation(Token.class);
					if (annotation != null) {
						ResultEntity resultEntity = (ResultEntity) returnValue;
						String token = saveToken();
						resultEntity.setToken(token);
					}
				}
			}else if(returnValue instanceof BaseEntity){
				Signature signature = point.getSignature();
				MethodSignature methodSignature = (MethodSignature) signature;
				Method method = methodSignature.getMethod();

				if (method != null) {
					Token annotation = method.getAnnotation(Token.class);
					if (annotation != null) {
						BaseEntity baseEntity = (BaseEntity) returnValue;
						String token = saveToken();
						baseEntity.setToken(token);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private String saveToken() {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		Map<String, String> tokenMap = (Map<String, String>) session.getAttribute(Constants.CURRENT_TOKEN);
		if (tokenMap == null) {
			tokenMap = new HashMap<String, String>();
			session.setAttribute(Constants.CURRENT_TOKEN, tokenMap);
		}
		String token = UUID.randomUUID().toString();
		tokenMap.put(token, "");
		session.setAttribute(Constants.CURRENT_TOKEN, tokenMap);
		return token;
	}

}
