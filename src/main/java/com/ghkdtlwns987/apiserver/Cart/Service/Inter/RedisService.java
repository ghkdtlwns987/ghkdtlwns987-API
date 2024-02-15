package com.ghkdtlwns987.apiserver.Cart.Service.Inter;

import java.time.Duration;
import java.util.Map;

public interface RedisService {
    /**
     * Redis에 key : value 형태로 데이터를 저장하는 방식입니다.
     * @param key
     * @param value
     */
    public void setValues(String key, String value);

    /**
     * redis에 key : value 형태로 데이터를 저장하며 TTl을 지정합니다.
     * @param key
     * @param value
     * @param ttl
     */
    public void setValues(String key, String value, Duration ttl);

    /**
     * Redis에 key값을 통해 값을 가져옵니다.
     *
     * @param Key
     * @return String
     */
    public String getValues(String Key);

    /**
     * Redis에 저장된 값을 삭제합니다.
     * @param key
     */
    public void deleteValues(String key);

    /**
     * Redis 만료시간을 key를 기반으로 ttl을 지정합니다.
     * @param key
     * @param ttl
     */
    public void expireValues(String key, int ttl);

    /**
     * Redis 에 저장하는 데이터를 Hash형태로 저장합니다.
     * @param key
     * @param data
     */
    public void setHashOps(String key, Map<String, String> data);

    /**
     * Redis 에 Hash 형태로 저장된 값을 가져옵니다.
     * @param key
     * @param key, hashKey
     */
    public String getHashOps(String key, String hashKey);

    /**
     * Redis 에 저장된 값을 삭제합니다.
     * @param key
     * @param hashKey
     */
    public void deleteHashOps(String key, String hashKey);

    /**
     * Redis 에 해당 데이터가 존재하는지 확인합니다.
     * @param value
     * @return
     */
    public boolean checkExistsValue(String value);
}
