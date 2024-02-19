package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandCartService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandRedisService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CommandCartServiceImpl implements CommandCartService {
    private final CommandCartRepository commandCartRepository;
    private final CommandRedisService commandRedisService;
    private final QueryRedisService queryRedisService;

    private static final Duration TTL = Duration.ofMillis(5000);

    @Override
    public CartDto saveCartForCache(String key, CartDto value) {
        return commandRedisService.setValues(key, value, TTL);
    }

    @Override
    public CartDto saveCartForEntity(String key, CartDto value) {
        //return commandCartRepository.save(value);
        return null;
    }
}
