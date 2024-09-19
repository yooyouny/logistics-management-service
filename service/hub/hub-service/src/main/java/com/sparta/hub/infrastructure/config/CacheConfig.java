package com.sparta.hub.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.cloud.openfeign.support.SortJsonComponent.SortDeserializer;
import org.springframework.cloud.openfeign.support.SortJsonComponent.SortSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.findAndRegisterModules(); // LocalDateTime 같은 타입을 위한 모듈 자동 등록
    objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
    // Spring Data 관련 모듈 추가 (Page, Pageable 처리)
//    objectMapper.registerModule(new PageJacksonModule());
    objectMapper.deactivateDefaultTyping();
    // GenericJackson2JsonRedisSerializer를 사용하여 ObjectMapper를 적용
    GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

    // RedisCacheConfiguration 설정
    RedisCacheConfiguration configuration = RedisCacheConfiguration
        .defaultCacheConfig()
        .disableCachingNullValues()
        .entryTtl(Duration.ofSeconds(120)) // 캐시 TTL 설정
        .computePrefixWith(CacheKeyPrefix.simple()) // 캐시 키 프리픽스
        .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())) // 키는 String
//        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer)); // 값은 JSON으로 직렬화
        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer())); // 값은 JSON으로 직렬화

    // RedisCacheManager 생성 및 반환
    return RedisCacheManager
        .builder(redisConnectionFactory)
        .cacheDefaults(configuration)
        .build();
  }
}
