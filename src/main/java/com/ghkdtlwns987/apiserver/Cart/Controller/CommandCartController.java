package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Repository.CommandRedisRepository;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandCartService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandRedisService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommandCartController {
    private final CommandRedisService commandRedisService;
    private final CommandCartService commandCartService;

    // RedisTTL은 60초로 고정
    private static final Duration TTL = Duration.ofMillis(60000);
    @PostMapping("/cart")
    public ResponseEntity saveData(@RequestBody CartDto request) {
        commandCartService.saveCartForEntity(request);
        CartDto response = commandRedisService.saveCartForCache(request.getUserId(), request);
        ResultResponse result = ResultResponse.of(ResultCode.CART_CREATE_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/cart")
    public ResponseEntity update(@RequestBody CartDto request) {
        commandCartService.saveCartForEntity(request);
        CartDto response = commandRedisService.saveCartForCache(request.getUserId(), request);
        ResultResponse result = ResultResponse.of(ResultCode.CART_UPDATE_SUCCESS, response);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @DeleteMapping("/cart")
    public ResponseEntity deleteData(@RequestParam String key){
        commandRedisService.deleteCartForCache(key);
        ResultResponse result = ResultResponse.of(ResultCode.CART_DELETE_SUCCESS, "데이터가 삭제 되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
