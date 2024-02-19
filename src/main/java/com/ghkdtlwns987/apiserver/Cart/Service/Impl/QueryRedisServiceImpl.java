package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryRedisServiceImpl implements QueryRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
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
    public boolean checkExistsValue(String value) {
        return !value.equals("false");
    }
}
