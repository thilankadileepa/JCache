package it.codegen.tbx;

import java.util.Random;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

public class CacheTest {

	public static void main(String[] args) {
		CachingProvider cachingProvider = Caching.getCachingProvider();
		CacheManager cacheManager = cachingProvider.getCacheManager();

		MutableConfiguration<String, Integer> config = new MutableConfiguration<String, Integer>()
				.setTypes(String.class, Integer.class)
				.setExpiryPolicyFactory(
						AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE))
				.setStatisticsEnabled(true);

		Cache<String, Integer> cache = cacheManager.getCache("simpleCache", String.class, Integer.class);		
		if (cache == null) {
			System.out.println("############## Create New Cache ###########");
			cache = cacheManager.createCache("simpleCache", config);
			populateCache(cache);
		}
		else
		{
			System.out.println("*************** Cache already created *************");
		}
		
		
		printContent(cache);
	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	/**
	 * we populate cache with (theKey-i, i )
	 */
	public static void populateCache(Cache<String, Integer> cache) {
		if (cache != null) {
			for (int i = 0; i < 10; i++) {
				cache.put("theKey-" + i, i);
			}
		}
	}

	/**
	 * print all of the content of the cache, if expires or not exist you will
	 * see a null value
	 */
	public static void printContent(Cache<String, Integer> cache) {
		System.out
				.println("==============>  " + cache.getName() + "@ URI:"
						+ cache.getCacheManager().getURI()
						+ "  <=====================");
		for (int i = 0; i < 10; i++) {
			final String key = "theKey-" + i;
			System.out.println("Key: " + key + ", Value: " + cache.get(key));
		}
		System.out
				.println("============================================================");
	}
}
