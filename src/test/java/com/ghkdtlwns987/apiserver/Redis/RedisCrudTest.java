package com.ghkdtlwns987.apiserver.Redis;

import com.ghkdtlwns987.apiserver.Cart.Service.Inter.RedisService;
import com.ghkdtlwns987.apiserver.IntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.awaitility.Awaitility.await;

import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class RedisCrudTest extends IntegrationTest{
    final String Key = "key";
    final String Value = "value";
    final Duration TTL = Duration.ofMillis(5000);

    @Autowired
    RedisService redisService;

    @BeforeEach
    void shutdown(){
        redisService.setValues(Key, Value, TTL);
    }

    @AfterEach
    void tearDown(){
        redisService.deleteValues(Key);
    }

    @Test
    @DisplayName("Redis 데이터 조회")
    void saveAndFindTest() throws Exception{
        String findValue = redisService.getValues(Key);
        assertThat(Value).isEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis 데이터 수정")
    void updateTest() throws Exception{
        String updateValue = "updateValue";
        redisService.setValues(Key, updateValue, TTL);

        String findValue = redisService.getValues(Key);

        assertThat(updateValue).isEqualTo(findValue);
        assertThat(Value).isNotEqualTo(findValue);
    }

    @Test
    @DisplayName("Redis 데이터 삭제")
    void deleteTest() throws Exception{
        redisService.deleteValues(Key);
        String findValue = redisService.getValues(Key);
        assertThat(findValue).isEqualTo("false");
    }

    @Test
    @DisplayName("Redis 데이터 만료시간 테스트")
    void expiredTest() throws Exception{
        String findValue = redisService.getValues(Key);
        await().pollDelay(Duration.ofMillis(6000)).untilAsserted(
                () -> {
                    String expiredValue = redisService.getValues(Key);
                    assertThat(expiredValue).isNotEqualTo(findValue);
                    assertThat(expiredValue).isEqualTo("false");
                }
        );
    }
}
