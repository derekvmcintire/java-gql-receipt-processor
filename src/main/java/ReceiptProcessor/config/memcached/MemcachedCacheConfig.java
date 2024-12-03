package ReceiptProcessor.config.memcached;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;

import java.util.concurrent.Callable;

import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableCaching
public class MemcachedCacheConfig {

  @Bean
  public MemcachedClient memcachedClient() throws Exception {
    // Provide the Memcached server address (host:port)
    XMemcachedClientBuilder builder = new XMemcachedClientBuilder("127.0.0.1:11211");
    return builder.build(); // Build and return the client
  }

  @Bean
  public CacheManager cacheManager(MemcachedClient memcachedClient) {
    return new SimpleMemcachedCacheManager(memcachedClient);
  }

  static class SimpleMemcachedCacheManager implements CacheManager {
    private final MemcachedClient memcachedClient;

    public SimpleMemcachedCacheManager(MemcachedClient memcachedClient) {
      this.memcachedClient = memcachedClient;
    }

    @Override
    public Cache getCache(String name) {
      return new SimpleMemcachedCache(name, memcachedClient);
    }

    @Override
    public Collection<String> getCacheNames() {
      return Collections.singletonList("receipt");
    }
  }

  static class SimpleMemcachedCache implements Cache {
    private final String name;
    private final MemcachedClient memcachedClient;

    public SimpleMemcachedCache(String name, MemcachedClient memcachedClient) {
      this.name = name;
      this.memcachedClient = memcachedClient;
    }

    @Override
    public String getName() {
      return this.name;
    }

    @Override
    public Object getNativeCache() {
      return this.memcachedClient;
    }

    @Override
    public ValueWrapper get(Object key) {
      try {
        Object value = memcachedClient.get(key.toString());
        return value != null ? () -> value : null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
      try {
        Object value = memcachedClient.get(key.toString());
        if (value != null) {
          return (T) value;
        }

        // Compute and store value if not present
        T computedValue = valueLoader.call();
        memcachedClient.set(key.toString(), 3600, computedValue); // Default TTL: 1 hour
        return computedValue;
      } catch (Exception e) {
        e.printStackTrace();
        throw new IllegalStateException("Failed to load value for key: " + key, e);
      }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
      try {
        Object value = memcachedClient.get(key.toString());
        if (value != null && type.isInstance(value)) {
          return type.cast(value);
        }
        return null;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }

    @Override
    public void put(Object key, Object value) {
      try {
        memcachedClient.set(key.toString(), 3600, value); // Default TTL: 1 hour
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void evict(Object key) {
      try {
        memcachedClient.delete(key.toString());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    @Override
    public void clear() {
      try {
        memcachedClient.flushAll();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
