package com.ghkdtlwns987.apiserver.Order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Order.Controller.QueryOrderController;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import com.ghkdtlwns987.apiserver.Order.Service.Inter.QueryOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.List;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(QueryOrderController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs

public class QueryOrderControllerTest {
    private String orderId = "f932204e-577e-4d17-9101-dd870b7416dd";
    private String userId = "1561d7eb-ab64-48a1-95c8-80d1602bd826i";
    private String productId1 = "CATALOG-0001";
    private String productId2 = "CATALOG-0002";
    private String productId3 = "CATALOG-0003";

    private String productName = "Berlin";
    private Integer qty = 100;
    private Integer unitPrice =1000;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime currentTime;

    RequestOrderDto requestOrderDto;
    ResponseOrderDto responseOrderDto1;
    ResponseOrderDto responseOrderDto2;
    ResponseOrderDto responseOrderDto3;
    List<ResponseOrderDto> responseOrderDtoList;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    QueryOrderService queryOrderService;

    @BeforeEach
    void setUp(){
        requestOrderDto = new RequestOrderDto(productId1, productName, qty, unitPrice);
        responseOrderDto1 = new ResponseOrderDto(productId1, qty, unitPrice, (qty * unitPrice), userId, orderId, currentTime);
        responseOrderDto2 = new ResponseOrderDto(productId2, qty, unitPrice, (qty * unitPrice), userId, orderId, currentTime);
        responseOrderDto3 = new ResponseOrderDto(productId3, qty, unitPrice, (qty * unitPrice), userId, orderId, currentTime);

        responseOrderDtoList = List.of(responseOrderDto1, responseOrderDto2, responseOrderDto3);
    }

    /**
     * {
     *     "status": 200,
     *     "code": "O001",
     *     "message": "주문 내역을 조회했습니다.",
     *     "data": [],
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     */
    @Test
    void 회원_주문내역_조회_주문하지_않았을경우_빈_배열_반환() throws Exception {
        String invalidUserId="invalud_userId";
        List<ResponseOrderDto> emptyResponses = List.of();
        // given
        when(queryOrderService.getOrderData(userId)).thenReturn(emptyResponses);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/order/orders/" + invalidUserId)
                        .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(document(
                "회원_주문내역_조회_주문하지_않았을경우_빈_배열_반환",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_ORDER_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_ORDER_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", equalTo(emptyResponses)))
                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost/api/v1/order")))
                ;
    }

    /**
     * {
     *     "status": 200,
     *     "code": "O001",
     *     "message": "주문 내역을 조회했습니다.",
     *     "data": [
     *         {
     *             "productId": "CATALOG-0003",
     *             "qty": 65,
     *             "unitPrice": 1000,
     *             "totalPrice": 65000,
     *             "userId": "a5736808-4298-4578-b3b4-23b8655e1c34",
     *             "orderId": "be8a3eae-8cfb-4333-b047-cf4cb7b0c67d",
     *             "orderedAt": "2024-02-18T14:57:19.683726"
     *         },
     *         {
     *             "productId": "CATALOG-0003",
     *             "qty": 1,
     *             "unitPrice": 1000,
     *             "totalPrice": 1000,
     *             "userId": "a5736808-4298-4578-b3b4-23b8655e1c34",
     *             "orderId": "83b4f2fd-7b7d-440e-9f70-09f2c1c73a13",
     *             "orderedAt": "2024-02-18T14:57:24.985429"
     *         },
     *         {
     *             "productId": "CATALOG-0002",
     *             "qty": 100,
     *             "unitPrice": 1000,
     *             "totalPrice": 100000,
     *             "userId": "a5736808-4298-4578-b3b4-23b8655e1c34",
     *             "orderId": "ef6fa380-aa23-46ec-8760-e21db81d0fa3",
     *             "orderedAt": "2024-02-18T15:06:45.733645"
     *         },
     *         {
     *             "productId": "CATALOG-0001",
     *             "qty": 100,
     *             "unitPrice": 1000,
     *             "totalPrice": 100000,
     *             "userId": "a5736808-4298-4578-b3b4-23b8655e1c34",
     *             "orderId": "36b0bd89-7db8-4e50-ae25-5f7b3d61fb39",
     *             "orderedAt": "2024-02-18T15:10:49.179313"
     *         }
     *     ],
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     */
    @Test
    void 회원_주문내역_조회() throws Exception {
// given
        when(queryOrderService.getOrderData(userId)).thenReturn(responseOrderDtoList);

        // when
        ResultActions perform = mockMvc.perform(get("/api/v1/order/orders/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        perform.andDo(document(
                "회원_주문내역_조회",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(ResultCode.GET_ORDER_REQUEST_SUCCESS.getCode()))
                .andExpect(jsonPath("$.message").value(ResultCode.GET_ORDER_REQUEST_SUCCESS.getMessage()))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(3)))

                .andExpect(jsonPath("$.data[0].productId").value(responseOrderDtoList.get(0).getProductId()))
                .andExpect(jsonPath("$.data[0].qty").value(responseOrderDtoList.get(0).getQty()))
                .andExpect(jsonPath("$.data[0].unitPrice").value(responseOrderDtoList.get(0).getUnitPrice()))
                .andExpect(jsonPath("$.data[0].totalPrice").value(responseOrderDtoList.get(0).getTotalPrice()))
                .andExpect(jsonPath("$.data[0].userId").value(responseOrderDtoList.get(0).getUserId()))
                .andExpect(jsonPath("$.data[0].orderId").value(responseOrderDtoList.get(0).getOrderId()))
                .andExpect(jsonPath("$.data[0].orderedAt", equalTo(currentTime)))

                .andExpect(jsonPath("$.data[1].productId").value(responseOrderDtoList.get(1).getProductId()))
                .andExpect(jsonPath("$.data[1].qty").value(responseOrderDtoList.get(1).getQty()))
                .andExpect(jsonPath("$.data[1].unitPrice").value(responseOrderDtoList.get(1).getUnitPrice()))
                .andExpect(jsonPath("$.data[1].totalPrice").value(responseOrderDtoList.get(1).getTotalPrice()))
                .andExpect(jsonPath("$.data[1].userId").value(responseOrderDtoList.get(1).getUserId()))
                .andExpect(jsonPath("$.data[1].orderId").value(responseOrderDtoList.get(1).getOrderId()))
                .andExpect(jsonPath("$.data[1].orderedAt", equalTo(currentTime)))

                .andExpect(jsonPath("$.data[2].productId").value(responseOrderDtoList.get(2).getProductId()))
                .andExpect(jsonPath("$.data[2].qty").value(responseOrderDtoList.get(2).getQty()))
                .andExpect(jsonPath("$.data[2].unitPrice").value(responseOrderDtoList.get(2).getUnitPrice()))
                .andExpect(jsonPath("$.data[2].totalPrice").value(responseOrderDtoList.get(2).getTotalPrice()))
                .andExpect(jsonPath("$.data[2].userId").value(responseOrderDtoList.get(2).getUserId()))
                .andExpect(jsonPath("$.data[2].orderId").value(responseOrderDtoList.get(2).getOrderId()))
                .andExpect(jsonPath("$.data[2].orderedAt", equalTo(currentTime)))

                .andExpect(jsonPath("$._links.self.href", equalTo("http://localhost/api/v1/order")))
        ;
    }
}
