/*
package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.ghkdtlwns987.apiserver.Cart.Service.Inter.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final RedisService redisService;

    // RedisTTL은 60초로 고정
    private static final Duration TTL = Duration.ofMillis(60000);
    @PostMapping("/create")
    public ResponseEntity saveData(@RequestBody RedisDto request) throws Exception{
        redisService.setValues(request.getKey(), request.getValue(), TTL);
        ResultResponse result = ResultResponse.of(ResultCode.CREATED_SUCCESS, "데이터가 생성되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/get")
    public ResponseEntity getData(@RequestParam String key) throws Exception{
        String data = redisService.getValues(key);
        ResultResponse result = ResultResponse.of(ResultCode.DATA_LOAD_SUCCESS, data);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody RedisDto redisDto) throws Exception{
        String updateValue = "updateValue";
        redisService.setValues(redisDto.getKey(), redisDto.getUpdateKey(), TTL);
        ResultResponse result = ResultResponse.of(ResultCode.UPDATE_SUCCESS, "Redis Key가 업데이트 됬습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteData(@RequestParam String key){
        redisService.deleteValues(key);
        ResultResponse result = ResultResponse.of(ResultCode.DELETE_SUCCESS, "데이터가 삭제 되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
 */