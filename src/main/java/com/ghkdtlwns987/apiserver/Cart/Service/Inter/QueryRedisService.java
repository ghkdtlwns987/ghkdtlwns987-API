package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

public interface QueryRedisService {
    /**
     * Redis에 key값을 통해 값을 가져옵니다.
     *
     * @param Key
     * @return String
     */
    CartDto getValues(String Key);

    /**
     * Redis 에 해당 데이터가 존재하는지 확인합니다.
     * @param key
     * @return
     */
    boolean checkExistsValue(String key);
}
