package com.ghkdtlwns987.apiserver.Order.Controller;

import com.ghkdtlwns987.apiserver.Global.ResultCode;
import com.ghkdtlwns987.apiserver.Member.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.CommandOrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CommandOrderController {
    private final CommandOrderService commandOrderService;

    @GetMapping("/orders/{userId}")
    public EntityModel<ResultResponse> getMemberOrders(@PathVariable String userId){
        List<ResponseOrderDto> response = commandOrderService.getOrderData(userId);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_MEMBER_ORDER_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandOrderController.class).withSelfRel());

        return entityModel;
    }
}
