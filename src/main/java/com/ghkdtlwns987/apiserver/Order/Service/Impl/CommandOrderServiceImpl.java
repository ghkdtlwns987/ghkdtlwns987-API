package com.ghkdtlwns987.apiserver.Order.Service.Impl;

import com.ghkdtlwns987.apiserver.Order.Command.CommandOrder;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.CommandOrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandOrderServiceImpl implements CommandOrderService {
    private final CommandOrder commandOrder;

    @Override
    public ResponseOrderDto createOrder(String userId, RequestOrderDto request) {
        return commandOrder.createOrderRequest(userId, request);
    }
}
