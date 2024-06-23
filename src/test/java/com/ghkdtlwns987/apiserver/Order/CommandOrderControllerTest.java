package com.ghkdtlwns987.apiserver.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Order.Controller.CommandOrderController;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.CommandOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CommandOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandOrderControllerTest {
    private String orderId = "f932204e-577e-4d17-9101-dd870b7416dd";
    private String userId = "1561d7eb-ab64-48a1-95c8-80d1602bd826i";
    private String productId = "CATALOG-0001";
    private String productName = "Berlin";
    private Integer qty = 100;
    private Integer unitPrice =1000;

    RequestOrderDto requestOrderDto;
    ResponseOrderDto responseOrderDto;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime currentTime;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommandOrderService commandOrderService;
    @BeforeEach
    void setUp(){
        requestOrderDto = new RequestOrderDto(productId, productName, qty, unitPrice);
        responseOrderDto = new ResponseOrderDto(productId, qty, unitPrice, (qty * unitPrice), userId, orderId, currentTime);
    }

    @Test
    void 주문생성_실패_재고_부족() throws Exception {
        // given
        when(commandOrderService.createOrder(any(String.class), any(RequestOrderDto.class)))
                .thenThrow(new ClientException(ErrorCode.OUT_OF_STOCK, "재고가 모두 소진되었습니다."));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/order/orders/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestOrderDto))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("재고가 모두 소진되었습니다.")));
    }

    /**
     * "message": "존재하지 않는 ProductId 입니다."
     */
    @Test
    void 주문생성_실패_존재하지_않는_productId() throws Exception {
        // given
        when(commandOrderService.createOrder(any(String.class), any(RequestOrderDto.class)))
                .thenThrow(new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, "존재하지 않는 ProductId 입니다."));

        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/order/orders/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestOrderDto))
        );

        // then
        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("존재하지 않는 ProductId 입니다.")));
    }

    /**
     * {
     *     "status": 200,
     *     "code": "O001",
     *     "message": "회원 주문이 접수되었습니다.",
     *     "data": {
     *         "productId": "CATALOG-0001",
     *         "qty": 100,
     *         "unitPrice": 1000,
     *         "totalPrice": 100000,
     *         "userId": "a5736808-4298-4578-b3b4-23b8655e1c34",
     *         "orderId": "36b0bd89-7db8-4e50-ae25-5f7b3d61fb39",
     *         "orderedAt": "2024-02-18T15:10:49.179313"
     *     },
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     * @throws Exception
     */
    @Test
    void 주문생성_성공() throws Exception {
        // given
        when(commandOrderService.createOrder(any(String.class), any(RequestOrderDto.class)))
                .thenReturn(responseOrderDto);
        // when
        ResultActions perform = mockMvc.perform(post("/api/v1/order/orders/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestOrderDto))
        );
        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CREATE_MEMBER_ORDER_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo("회원 주문이 접수되었습니다.")))
                .andExpect(jsonPath("$.data.productId", equalTo(responseOrderDto.getProductId())))
                .andExpect(jsonPath("$.data.qty", equalTo(responseOrderDto.getQty())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(responseOrderDto.getUnitPrice())))
                .andExpect(jsonPath("$.data.totalPrice", equalTo(responseOrderDto.getTotalPrice())))
                .andExpect(jsonPath("$.data.userId", equalTo(responseOrderDto.getUserId())))
                .andExpect(jsonPath("$.data.orderId", equalTo(responseOrderDto.getOrderId())))
                .andExpect(jsonPath("$.data.orderedAt", equalTo(currentTime)))
        ;
    }
}