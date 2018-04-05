package org.erian.examples.bootapi.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	
	@Override
	@Bean
	public CacheManager cacheManager() {
	 SimpleCacheManager simpleCacheManager = new SimpleCacheManager
			 ();
	 GuavaCache SEC01 = new GuavaCache("SEC01", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(1, TimeUnit.SECONDS)
             .build());
	 GuavaCache SEC02 = new GuavaCache("SEC02", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(2, TimeUnit.SECONDS)
             .build());
	 GuavaCache SEC05 = new GuavaCache("SEC05", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(5, TimeUnit.SECONDS)
             .build());
	 GuavaCache MIN01 = new GuavaCache("MIN01", CacheBuilder.newBuilder().maximumSize(1000)
	             .expireAfterAccess(1, TimeUnit.MINUTES)
	             .build());
	 GuavaCache MIN05 = new GuavaCache("MIN05", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(5, TimeUnit.MINUTES)
             .build());
	 GuavaCache SOCKET = new GuavaCache("SOCKET", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(1, TimeUnit.HOURS)
             .build());
	 GuavaCache TOKEN = new GuavaCache("TOKEN", CacheBuilder.newBuilder().maximumSize(1000)
             .expireAfterAccess(30, TimeUnit.MINUTES)
             .build());
	 simpleCacheManager.setCaches(Arrays.asList(SEC01,SEC02, SEC05,MIN01, MIN05,SOCKET, TOKEN));
	 return simpleCacheManager;
	}
	
	
/*	@Bean
	public CacheManager oneMinCacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(1,
				TimeUnit.MINUTES);
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}*/
}