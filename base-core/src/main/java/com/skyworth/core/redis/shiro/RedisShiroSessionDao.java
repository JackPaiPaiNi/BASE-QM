package com.skyworth.core.redis.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisShiroSessionDao extends AbstractSessionDAO {	
	private static Logger logger = LoggerFactory.getLogger(RedisShiroSessionDao.class);
	
	private String name = "session"; //缓存名称
	private RedisTemplate<String, Session> redisTemplate;
	private String delimiter = ":"; //分隔符
	private String prefix = "SKY"; //前缀，用于区分不同系统
	private long defaultExpiration = 1800;// 有效期半个小时;
	
	private String getKey(String originalKey) {
		return getCacheName(name) + originalKey;
	}
	
	private String getCacheName(String cacheName) {
		return prefix + delimiter + cacheName + delimiter;
	}
	
	@Override
	public void update(Session session) throws UnknownSessionException {
		logger.debug("更新seesion,id=[{}]", session.getId().toString());
		try {
			redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, defaultExpiration, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	@Override
	public void delete(Session session) {
		logger.debug("删除seesion,id=[{}]", session.getId().toString());
		try {
			String key=getKey(session.getId().toString());
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

	}

	@Override
	public Collection<Session> getActiveSessions() {
		logger.info("获取存活的session");
		return Collections.emptySet();
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = generateSessionId(session);
		assignSessionId(session, sessionId);
		logger.debug("创建seesion,id=[{}]", session.getId().toString());
		try {
			redisTemplate.opsForValue().set(getKey(session.getId().toString()), session, defaultExpiration, TimeUnit.SECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {

		logger.debug("获取seesion,id=[{}]", sessionId.toString());
		Session readSession = null;
		try {
			readSession=redisTemplate.opsForValue().get(getKey(sessionId.toString()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return readSession;
	}

	public RedisTemplate<String, Session> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Session> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getDefaultExpiration() {
		return defaultExpiration;
	}

	public void setDefaultExpiration(long defaultExpiration) {
		this.defaultExpiration = defaultExpiration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
