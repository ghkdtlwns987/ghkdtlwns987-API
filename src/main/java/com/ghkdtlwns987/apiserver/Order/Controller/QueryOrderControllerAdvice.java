package com.ghkdtlwns987.apiserver.Order.Controller;

import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.QueryOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class QueryOrderControllerAdvice {
    private final QueryOrderService queryOrderService;
    @GetMapping("/orders/{userId}")
    public EntityModel<ResultListResponse> getMemberOrders(@PathVariable String userId) throws Exception{
        List<ResponseOrderDto> response = queryOrderService.getOrderData(userId);
        ResultListResponse resultResponse = ResultListResponse.of(ResultCode.GET_ORDER_REQUEST_SUCCESS, response);
        EntityModel<ResultListResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandOrderController.class).withSelfRel());

        return entityModel;
    }
}
