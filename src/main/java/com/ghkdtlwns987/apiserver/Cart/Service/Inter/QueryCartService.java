package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

public interface QueryCartService {
    /**
     * Redis에 key값을 통해 값을 가져옵니다.
     *
     * @param Key
     * @return String
     */
    CartDto getValues(String Key);
}
