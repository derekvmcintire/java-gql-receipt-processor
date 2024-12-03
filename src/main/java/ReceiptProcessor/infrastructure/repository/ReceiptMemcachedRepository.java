package ReceiptProcessor.infrastructure.repository;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import ReceiptProcessor.api.dto.ReceiptResponse;

@Repository
public class ReceiptMemcachedRepository {

  private final CacheManager cacheManager;

  // Injecting the cacheManager instance through the constructor
  public ReceiptMemcachedRepository(CacheManager cacheManager) {
    this.cacheManager = cacheManager;
  }

  /**
   * Save a receipt into Memcached.
   * 
   * @param receipt The receipt to save.
   * @return The saved receipt.
   */
  public Integer saveToCache(ReceiptResponse receipt) {
    // Create a cache key using the receipt ID
    String cacheKey = "receipt-" + receipt.getId();
    Integer points = receipt.getPoints();

    // Get the cache from CacheManager and save the receipt into Memcached
    Cache cache = cacheManager.getCache("receipt");
    if (cache != null) {
      cache.put(cacheKey, points);
    }
    return points;
  }

  /**
   * Retrieve a receipt from Memcached.
   * 
   * @param id The ID of the receipt to retrieve.
   * @return The points associated with a receipt if found in cache, otherwise
   *         null.
   */
  public Integer findInCache(String id) {
    // Create a cache key using the receipt ID
    String cacheKey = "receipt-" + id;

    // Get the cache from CacheManager and retrieve the points from Memcached
    Cache cache = cacheManager.getCache("receipt"); // "receipt" is the cache name
    if (cache != null) {
      System.out.println("*************** Retrieving from cache with key: " + cacheKey);
      // Retrieve the value as an Integer (not a primitive int)
      return cache.get(cacheKey, Integer.class); // Use Integer.class here
    }
    return null; // Return null if cache is not found
  }

}
