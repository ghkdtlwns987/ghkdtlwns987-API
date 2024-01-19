package com.ghkdtlwns987.apiserver.Order.Service.Inter;

import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommandOrderService {
    /**
     * Order 서비스에 주문을 요청하는 서비스 입니다.
     * @param userId
     * @return ResopnseEntity<List<ResponseOrderDto>>
     */
    List<ResponseOrderDto> getOrderData(String userId);

    ResponseOrderDto createOrder(String userId, RequestOrderDto request);
}
