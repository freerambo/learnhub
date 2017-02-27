package org.rambo.spring.boot.cache;

import java.util.concurrent.TimeUnit;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Override
	@Bean
	public CacheManager cacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		return cacheManager;
	}

	@Bean
	public CacheManager oneMinCacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1,
				TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}

	@Bean
	public CacheManager oneSecCacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1,
				TimeUnit.SECONDS);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}
}