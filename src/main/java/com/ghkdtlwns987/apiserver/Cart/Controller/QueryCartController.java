package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class QueryCartController {
    private final QueryRedisService queryRedisService;
    @GetMapping("/search")
    public ResponseEntity getData(@RequestParam String key){
        CartDto data = queryRedisService.getValues(key);
        ResultResponse result = ResultResponse.of(ResultCode.CART_LOAD_SUCCESS, data);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }
}
