package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Entity.Cart;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Repository.QueryCartRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryCartService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryCartServiceImpl implements QueryCartService {
    private static final Duration TTL = Duration.ofMillis(60000);
    private final QueryRedisService queryRedisService;
    private final CommandRedisRepository commandRedisRepository;
    private final QueryCartRepository queryCartRepository;
    @Override
    @Transactional
    public CartDto getCartInfo(String key) {
        if(queryRedisService.checkExistsValue(key)){
            return queryRedisService.getValues(key);
        }

        Cart cart = queryCartRepository.findCartByUserId(key)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.CART_NOT_EXISTS,
                        "Cart Not Exists: " + key
                ));

        CartDto cartDto = CartDto.fromEntity(cart);
        commandRedisRepository.setValues(key, cartDto, TTL);
        return cartDto;
    }
}
