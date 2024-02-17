package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;

import java.time.Duration;
import java.util.Map;

public interface CommandCartService {
    /**
     * Redis에 key : value 형태로 데이터를 저장하는 방식입니다.
     * @param key
     * @param value
     */
    void setValues(String key, String value);

    /**
     * redis에 key : value 형태로 데이터를 저장하며 TTl을 지정합니다.
     * @param key
     * @param value
     * @param ttl
     */
    void setValues(String key, String value, Duration ttl);

    /**
     * redis CartDto를 에 key : value 형태로 데이터를 저장하며 TTl을 지정합니다.
     * @param key
     * @param value
     * @param ttl
     */
    CartDto setValues(String key, CartDto value, Duration ttl);

    /**
     * Redis에 저장된 값을 삭제합니다.
     * @param key
     */
    void deleteValues(String key);

    /**
     * Redis 만료시간을 key를 기반으로 ttl을 지정합니다.
     * @param key
     * @param ttl
     */
    void expireValues(String key, int ttl);

    /**
     * Redis 에 저장하는 데이터를 Hash형태로 저장합니다.
     * @param key
     * @param data
     */
    void setHashOps(String key, Map<String, String> data);

    /**
     * Redis 에 Hash 형태로 저장된 값을 가져옵니다.
     * @param key
     * @param key, hashKey
     */
    String getHashOps(String key, String hashKey);

    /**
     * Redis 에 저장된 값을 삭제합니다.
     * @param key
     * @param hashKey
     */
    void deleteHashOps(String key, String hashKey);

    /**
     * Redis 에 해당 데이터가 존재하는지 확인합니다.
     * @param value
     * @return
     */
    boolean checkExistsValue(String value);
}
