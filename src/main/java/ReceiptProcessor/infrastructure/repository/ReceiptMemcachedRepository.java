package ReceiptProcessor.infrastructure.repository;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
   * @throws JsonProcessingException
   */
  public ReceiptResponse saveToCache(ReceiptResponse receipt) throws JsonProcessingException {
    // Create a cache key using the receipt ID
    String cacheKey = "receipt-" + receipt.getId();

    // Get the cache from CacheManager and save the receipt into Memcached
    Cache cache = cacheManager.getCache("receipt"); // "default" is the cache name from your config
    if (cache != null) {
      System.out.println("*********** Saving to cache with key: " + cacheKey);

      ObjectMapper objectMapper = new ObjectMapper();
      try {
        String stringReceipt = objectMapper.writeValueAsString(receipt); // This line may throw JsonProcessingException
        cache.put(cacheKey, stringReceipt);
      } catch (JsonProcessingException e) {
        // Handle the exception, log it, or throw a custom exception
        System.err.println("Error while converting receipt to JSON: " + e.getMessage());
        throw e; // Re-throwing the exception so it can be handled by the caller
      }
    }
    System.out.println("******** CACHE IS NULL ************** ");
    return receipt;
  }

  /**
   * Retrieve a receipt from Memcached.
   * 
   * @param id The ID of the receipt to retrieve.
   * @return The receipt if found in cache, otherwise null.
   */
  public ReceiptResponse findInCache(String id) {
    // Create a cache key using the receipt ID
    String cacheKey = "receipt-" + id;

    // Get the cache from CacheManager and retrieve the receipt from Memcached
    Cache cache = cacheManager.getCache("receipt"); // "receipt" is the cache name from your config
    if (cache != null) {
      System.out.println("*************** Retrieving from cache with key: " + cacheKey);
      return cache.get(cacheKey, ReceiptResponse.class);
    }
    System.out.println("******** CACHE IS NULL ************** ");
    return null;
  }
}
