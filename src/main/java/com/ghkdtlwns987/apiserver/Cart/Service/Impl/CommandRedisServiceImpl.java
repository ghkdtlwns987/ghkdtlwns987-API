package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CommandRedisServiceImpl implements CommandRedisService {
    private final CommandRedisRepository commandRedisRepository;

    private static final Duration TTL = Duration.ofMillis(5000);
    @Override
    public CartDto saveCartForCache(String key, CartDto value) {
        return commandRedisRepository.setValues(key, value, TTL);
    }

    @Override
    public void deleteCartForCache(String key) {
        commandRedisRepository.deleteValues(key);
    }
}
