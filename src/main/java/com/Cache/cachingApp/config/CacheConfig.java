package com.Cache.cachingApp.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

       // Inside cacheDefaults() it requires RedisCacheConfiguration so we have to define RedisCacheConfiguration
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .prefixCacheNameWith("my-redis-")
                .entryTtl(Duration.ofSeconds(60))  // Time to Live(TTL) = 1 Minute
                .enableTimeToIdle() // Time To Idle(TTI) = reset the TTL
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // Defines how
                // Redis will store the keys , StringRedisSerializer is used to store cache keys in human-readable String format in Redis,
                // making it easier to debug and view in tools like RedisInsight.
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                        new GenericJackson2JsonRedisSerializer())); // Defines how Redis will store the values (data objects) ,
        // GenericJackson2JsonRedisSerializer converts Java objects into JSON before storing them in Redis and converts them
        // back to Java when fetched — ensuring data readability and compatibility.


        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();

    }
}
