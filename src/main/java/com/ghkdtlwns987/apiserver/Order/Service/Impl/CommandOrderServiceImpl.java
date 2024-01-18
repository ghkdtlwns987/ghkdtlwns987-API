package com.ghkdtlwns987.apiserver.Order.Service.Impl;

import com.ghkdtlwns987.apiserver.Order.Command.OrderCommand;
import com.ghkdtlwns987.apiserver.Order.Config.OrderConfig;
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
    private final OrderCommand orderCommand;

    @Override
    public List<ResponseOrderDto> getOrderData(String userId) {
        return orderCommand.getOrderData(userId);
    }
}
