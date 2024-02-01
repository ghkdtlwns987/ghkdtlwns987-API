package com.ghkdtlwns987.apiserver.Order.Service.Impl;

import com.ghkdtlwns987.apiserver.Order.Command.QueryOrder;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryOrderServiceImpl implements QueryOrderService {
    private final QueryOrder queryOrder;
    @Override
    public List<ResponseOrderDto> getOrderData(String userId) {
        return queryOrder.getOrderDataRequest(userId);
    }
}
