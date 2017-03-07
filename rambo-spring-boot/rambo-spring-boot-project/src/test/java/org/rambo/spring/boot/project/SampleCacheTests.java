package org.rambo.spring.boot.project;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleCacheTests {

	@Autowired
	private CacheManager cacheManager;
	@Test
	public void validateCache() {
		Cache deviceValues = this.cacheManager.getCache("deviceValues");
		
		assertThat(deviceValues).isNotNull();
		deviceValues.clear(); // Simple test assuming the cache is empty
		assertThat(deviceValues.get("BE")).isNull();
		
		String key = "foo";
		String value = "boo";

		deviceValues.putIfAbsent(key, value);
		String result = deviceValues.get(key, String.class);

		assertEquals(value, result);
	}

}