//package com.seoultech.dayo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.CacheKeyPrefix;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder;
//import org.springframework.data.redis.connection.RedisConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//
//  @Value("${spring.redis.host}")
//  private String host;
//
//  @Value("${spring.redis.port}")
//  private int port;
//
//  @Bean
//  public RedisConnectionFactory redisConnectionFactory() {
//    return new LettuceConnectionFactory(host, port);
//  }
//
//  @Bean
//  public CacheManager cacheManager() {
//    RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
//        .disableCachingNullValues()
//        .computePrefixWith(CacheKeyPrefix.simple())
//        .serializeKeysWith(SerializationPair.fromSerializer(
//            new StringRedisSerializer()));
//
//    return RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
//        .cacheDefaults(configuration)
//        .build();
//
//
//  }
//
//}
