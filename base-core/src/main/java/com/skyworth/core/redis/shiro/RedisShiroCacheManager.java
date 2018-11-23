package com.skyworth.core.redis.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisShiroCacheManager implements CacheManager {

	private static final Logger logger = LoggerFactory.getLogger(RedisShiroCacheManager.class);
	
	private RedisTemplate<String, Session> redisTemplate;
	private String delimiter = ":"; //分隔符
	private String prefix = "SKY"; //前缀，用于区分不同系统
	private long defaultExpiration = 1800;// 有效期半个小时;
	
	private String getCacheName(String cacheName) {
		return prefix + delimiter + cacheName + delimiter;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		logger.debug("获取缓存,id=[{}]", name);

		return new RedisShiroCache(getCacheName(name), redisTemplate, defaultExpiration);
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
}
