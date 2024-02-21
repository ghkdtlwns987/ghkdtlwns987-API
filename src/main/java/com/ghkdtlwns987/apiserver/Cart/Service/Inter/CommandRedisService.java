package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

public interface CommandRedisService {
    /**
     * 캐시에 장바구니에 값을 저장합니다.
     * @param key
     * @param value
     * @return
     */
    CartDto saveCartForCache(String key, CartDto value);

    /**
     * 캐시에 저장된 장바구니를 삭제합니다.
     * @param key
     */
    void deleteCartForCache(String key);
}
