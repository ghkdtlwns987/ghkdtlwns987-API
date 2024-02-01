package com.ghkdtlwns987.apiserver.Order.Service.Inter;

import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;

import java.util.List;

public interface QueryOrderService {
    List<ResponseOrderDto> getOrderData(String userId);
}
