package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Repository.QueryCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommandCartServiceImpl implements CommandCartService {
    private final CommandCartRepository commandCartRepository;
    @Override
    public CartDto saveCartForEntity(CartDto cartDto) {
        Cart cart = cartDto.toEntity();
        Cart savedCart = commandCartRepository.save(cart);
        return CartDto.fromEntity(savedCart);
    }
}
