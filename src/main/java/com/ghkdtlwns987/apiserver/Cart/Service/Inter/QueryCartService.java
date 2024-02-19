package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

public interface QueryCartService {
    /**
     * 장바구니에 담긴 정보를 가져옵니다.
     * 캐싱 전략으로는 Look Aside + Write Around 전략을 채택했습니다.
     * @param key
     * @return 황시준
     */
    CartDto getCartInfo(String key);
}
