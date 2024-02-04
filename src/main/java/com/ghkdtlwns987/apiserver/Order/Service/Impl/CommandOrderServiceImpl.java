package com.ghkdtlwns987.apiserver.Order.Service.Impl;

import com.ghkdtlwns987.apiserver.Order.Command.CommandOrder;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.CommandOrderService;

import com.ghkdtlwns987.apiserver.Order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandOrderServiceImpl implements CommandOrderService {
    private final CommandOrder commandOrder;

    @Override
    public ResponseOrderDto createOrder(String userId, RequestOrderDto request) throws ServerException {
        return commandOrder.createOrderRequest(userId, request);
    }
}
