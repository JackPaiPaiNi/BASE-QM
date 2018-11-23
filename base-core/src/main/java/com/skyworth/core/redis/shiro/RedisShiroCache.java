package com.skyworth.core.redis.shiro;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * redis的基础存储方式，此hash不一样，可以单独设置每一条数据的失效时间
 * 
 * @param <K>
 * @param <V>
 */
public class RedisShiroCache<K, V> implements Cache<K, V>, Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private RedisTemplate<K, V> redisTemplate;
	private long defaultExpiration = 0;

	public RedisShiroCache(String name, RedisTemplate<K, V> template, long defaultExpiration) {
		this.name = name;
		this.redisTemplate = template;
		this.defaultExpiration = defaultExpiration;
	}

	@Override
    public V get(K key) throws CacheException {
        redisTemplate.boundValueOps(getCacheKey(key)).expire(defaultExpiration, TimeUnit.SECONDS);
        return redisTemplate.boundValueOps(getCacheKey(key)).get();
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V old = get(key);
        redisTemplate.boundValueOps(getCacheKey(key)).set(value);
        return old;
    }

	@Override
	public V remove(K key) throws CacheException {
		V old = get(key);
		redisTemplate.delete(getCacheKey(key));
		return old;
	}

	@Override
	public void clear() throws CacheException {
		redisTemplate.delete(keys());
	}

	@Override
	public int size() {
		return keys().size();
	}

	@Override
	public Set<K> keys() {
		return redisTemplate.keys(getCacheKey("*"));
	}

	@Override
	public Collection<V> values() {
		Set<K> set = keys();
		List<V> list = new LinkedList<V>();
		for (K s : set) {
			list.add(get(s));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private K getCacheKey(Object k) {
		return (K) (this.name + k);
	}
}