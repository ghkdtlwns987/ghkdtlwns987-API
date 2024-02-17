package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CartService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    // RedisTTL은 60초로 고정
    private static final Duration TTL = Duration.ofMillis(60000);
    @PostMapping("/create")
    public ResponseEntity saveData(@RequestBody CartDto request) {
        cartService.setValues(request.getUserId(), request, TTL);
        ResultResponse result = ResultResponse.of(ResultCode.CART_CREATE_SUCCESS, "데이터가 생성되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/get")
    public ResponseEntity getData(@RequestParam String key){
        CartDto data = cartService.getValues(key);
        ResultResponse result = ResultResponse.of(ResultCode.CART_LOAD_SUCCESS, data);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/update")
    public ResponseEntity update(@RequestBody CartDto request) {
        cartService.setValues(request.getUserId(), request, TTL);
        ResultResponse result = ResultResponse.of(ResultCode.CART_UPDATE_SUCCESS, "Redis Key가 업데이트 됬습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
    @DeleteMapping("/delete")
    public ResponseEntity deleteData(@RequestParam String key){
        cartService.deleteValues(key);
        ResultResponse result = ResultResponse.of(ResultCode.CART_DELETE_SUCCESS, "데이터가 삭제 되었습니다.");
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
