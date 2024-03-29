package com.ghkdtlwns987.apiserver.Cart.Repository;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * CommandRedisRepository 구현체입니다.
 * 설명은 CommandRedisRepository.java 에 있습니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Repository
@RequiredArgsConstructor
public class CommandRedisRepositoryImpl implements CommandRedisRepository {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setValues(String key, String value) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value);
    }

    @Override
    public void setValues(String key, String value, Duration ttl) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, ttl);
    }

    @Override
    public CartDto setValues(String key, CartDto value, Duration ttl) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, ttl);
        return value;
    }

    @Override
    public void deleteValues(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expireValues(String key, int ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.MICROSECONDS);
    }

    @Override
    public void setHashOps(String key, Map<String, String> data) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.putAll(key, data);
    }

    @Transactional(readOnly = true)
    @Override
    public String getHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        return Boolean.TRUE.equals(values.hasKey(key, hashKey)) ? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
    }

    @Override
    public void deleteHashOps(String key, String hashKey) {
        HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
        values.delete(key, hashKey);
    }

}
