package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CommandCartServiceImpl implements CommandCartService {
    private final CommandCartRepository commandCartRepository;
    private final CommandRedisRepository commandRedisRepository;

    private static final Duration TTL = Duration.ofMillis(5000);

    @Override
    public CartDto saveCartForCache(String key, CartDto value) {
        return commandRedisRepository.setValues(key, value, TTL);
    }

    @Override
    public CartDto saveCartForEntity(CartDto cartDto) {
        Cart cart = cartDto.toEntity();
        Cart savedCart = commandCartRepository.save(cart);
        return CartDto.fromEntity(savedCart);
    }
}
