package com.ghkdtlwns987.apiserver.Cart.Service.Impl;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandRedisService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryCartService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryCartServiceImpl implements QueryCartService {
    private final QueryRedisService queryRedisService;
    @Override
    public CartDto getCartInfo(String key) {
        if(queryRedisService.checkExistsValue(key)){
            return queryRedisService.getValues(key);
        }

        return null;
    }
}
