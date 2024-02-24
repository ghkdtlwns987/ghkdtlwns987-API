package com.ghkdtlwns987.apiserver.Catalog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Command.CommandCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Controller.CommandCatalogController;
import com.ghkdtlwns987.apiserver.Catalog.Controller.QueryCatalogController;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.rmi.ServerException;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandCatalogController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandProductsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CommandCatalog commandCatalog;

    RequestCatalogDto requestCatalogDto;
    ResponseCatalogDto responseCatalogDto;
    private String productId = "CATALOG-0001";
    private String productName = "Berlin";
    private Integer qty = 100;
    private Integer unitPrice =1000;
    private String orderId = "f932204e-577e-4d17-9101-dd870b7416dd";
    private String userId = "1561d7eb-ab64-48a1-95c8-80d1602bd826i";

    @BeforeEach
    void setUp() {
        requestCatalogDto = new RequestCatalogDto(productId, productName, qty, unitPrice, orderId, userId);
        responseCatalogDto = new ResponseCatalogDto(productId, productName, qty, unitPrice);
    }


    /**
     * {
     *     "message": "ProductId 가 이미 존재합니다."
     * }
     */
    @Test
    void 상품_등록_실패_이미_존재하는_productId() throws Exception {
        when(commandCatalog.createCatalogRequest(any(RequestCatalogDto.class))).thenThrow(
            new ClientException(ErrorCode.PRODUCT_ID_ALREADY_EXISTS, ErrorCode.PRODUCT_ID_ALREADY_EXISTS.getMessage()));

        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCatalogDto))
        );

        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo(ErrorCode.PRODUCT_ID_ALREADY_EXISTS.getMessage())));
    }
    /**
     * {
     *     "status": 200,
     *     "code": "C001",
     *     "message": "상품 등록이 완료되었습니다.",
     *     "data": {
     *         "productId": "CATALOG-00062",
     *         "productName": "Berlin",
     *         "stock": 100,
     *         "unitPrice": 1500
     *     },
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     */
    @Test
    void 상품_등록_성공() throws Exception{
        when(commandCatalog.createCatalogRequest(any(RequestCatalogDto.class))).thenReturn(responseCatalogDto);


        ResultActions perform = mockMvc.perform(post("/api/v1/catalog/catalog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestCatalogDto))
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CREATE_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CREATE_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.productId", equalTo(responseCatalogDto.getProductId())))
                .andExpect(jsonPath("$.data.productName", equalTo(responseCatalogDto.getProductName())))
                .andExpect(jsonPath("$.data.stock", equalTo(responseCatalogDto.getStock())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(responseCatalogDto.getUnitPrice())))
        ;
        verify(commandCatalog, times(1)).createCatalogRequest(any(RequestCatalogDto.class));
    }
}
