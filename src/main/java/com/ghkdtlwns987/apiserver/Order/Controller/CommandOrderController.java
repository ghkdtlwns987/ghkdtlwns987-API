package com.ghkdtlwns987.apiserver.Order.Controller;

import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.CommandOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CommandOrderController {
    private final CommandOrderService commandOrderService;

    @GetMapping("/orders/{userId}")
    public EntityModel<ResultListResponse> getMemberOrders(@PathVariable String userId) throws Exception{
        List<ResponseOrderDto> response = commandOrderService.getOrderData(userId);
        ResultListResponse resultResponse = ResultListResponse.of(ResultCode.GET_ORDER_REQUEST_SUCCESS, response);
        EntityModel<ResultListResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandOrderController.class).withSelfRel());

        return entityModel;
    }

    @PostMapping("/orders/{userId}")
    public EntityModel<ResultResponse> createOrders(@PathVariable String userId, @RequestBody RequestOrderDto request) throws Exception{
        ResponseOrderDto response = commandOrderService.createOrder(userId, request);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.CREATE_MEMBER_ORDER_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandOrderController.class).withSelfRel());

        return entityModel;
    }
}
