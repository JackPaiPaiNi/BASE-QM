package com.skyworth.core.redis;

import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义redis缓存前缀
 * 
 */
public class RedisCachePerfixGenerate implements RedisCachePrefix {

	private final RedisSerializer<String> serializer = new StringRedisSerializer();
	private String delimiter; //分隔符
	private String prefix; //前缀，用于区分不同系统

	public RedisCachePerfixGenerate() {
		this(":", "SKY");
	}

	public RedisCachePerfixGenerate(String delimiter, String prefix) {
		this.delimiter = delimiter;
		this.prefix = prefix;
	}

	public byte[] prefix(String cacheName) {
		return serializer.serialize(prefix + delimiter + cacheName + delimiter);
	}
}