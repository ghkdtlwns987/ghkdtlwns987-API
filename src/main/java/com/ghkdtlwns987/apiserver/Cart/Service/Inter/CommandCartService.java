package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

public interface CommandCartService {
    /**
     * 캐시에 장바구니에 값을 저장합니다.
     * @param key
     * @param value
     * @return
     */
    CartDto saveCartForCache(String key, CartDto value);

    /**
     * 데이터베이스에 장바구니에 값을 저장합니다.
     * @param key
     * @param value
     * @return
     */
    CartDto saveCartForEntity(CartDto value);
}
