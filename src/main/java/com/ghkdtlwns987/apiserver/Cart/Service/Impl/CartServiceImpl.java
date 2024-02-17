package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * CartService 구현체입니다.
 * 설명은 CartService.java 에 있습니다.
 * @author : 황시준
 * @since  : 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

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
    public void setValues(String key, CartDto value, Duration ttl) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        values.set(key, value, ttl);
    }

    @Transactional(readOnly = true)
    @Override
    public CartDto getValues(String key) {
        ValueOperations<String, Object> values = redisTemplate.opsForValue();
        if (values.get(key) == null) {
            return null;
        }

        try {
            String jsonValue = objectMapper.writeValueAsString(values.get(key));
            return objectMapper.readValue(jsonValue, CartDto.class);
        } catch (JsonProcessingException e) {
            log.error("Json 파싱 에러");
            e.printStackTrace();
            return null;
        }
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

    @Override
    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }

}
