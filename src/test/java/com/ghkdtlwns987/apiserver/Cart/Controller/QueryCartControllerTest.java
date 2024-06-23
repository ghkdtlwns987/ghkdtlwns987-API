package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
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

import java.time.Duration;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(QueryCartController.class)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
public class QueryCartControllerTest {
    final Duration TTL = Duration.ofMillis(5000);
    private String userId = "7721e64d-c600-4e30-9f22-cdc3262eebde";
    private String name = "장바구니 이름";
    private String description = "상품 설명";
    private String productId1 = "CATALOG-001";
    private String productId2 = "CATALOG-002";
    private String productName1 = "상품 1";
    private String productName2 = "상품 2";
    private Integer qty1 = 3;
    private Integer qty2 = 5;
    private Integer unitPrice1 = 5000;
    private Integer unitPrice2 = 8000;
    CartDto cartDto;
    CartDto.Catalogs catalogs1;
    CartDto.Catalogs catalogs2;
    List<CartDto.Catalogs> catalogsList;
    CartDto.Catalog catalog;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    QueryRedisService queryRedisService;
    @BeforeEach
    void setUp(){
        catalogs1 = CartDto.Catalogs.builder()
                .productId(productId1)
                .productName(productName1)
                .qty(qty1)
                .unitPrice(unitPrice1)
                .build();

        catalogs2 = CartDto.Catalogs.builder()
                .productId(productId2)
                .productName(productName2)
                .qty(qty2)
                .unitPrice(unitPrice2)
                .build();

        catalogsList = List.of(catalogs1, catalogs2);
        catalog = new CartDto.Catalog(name, description, catalogsList);

        cartDto = new CartDto(userId, List.of(catalog));
    }

    @Test
    void 장바구니_조회_실패_존재하지_않는_key() throws Exception {
        when(queryRedisService.getValues(any(String.class))).thenReturn(null);

        ResultActions perform = mockMvc.perform(get("/api/v1/cart/search")
                .param("key", userId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(document("장바구니_조회_실패_존재하지_않는_key",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())
                        ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CART_LOAD_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CART_LOAD_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", equalTo(null)));

        verify(queryRedisService, times(1)).getValues(any(String.class));
    }
    @Test
    void 장바구니_조회_성공() throws Exception {
        when(queryRedisService.getValues(any(String.class))).thenReturn(cartDto);

        ResultActions perform = mockMvc.perform(get("/api/v1/cart/search")
                .param("key", userId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(document("장바구니_조회_성공",
                        Preprocessors.preprocessRequest(prettyPrint()),
                        Preprocessors.preprocessResponse(prettyPrint())
                ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CART_LOAD_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CART_LOAD_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.userId", equalTo(cartDto.getUserId())))
                .andExpect(jsonPath("$.data.carts[0].name", equalTo(cartDto.getCarts().get(0).getName())))
                .andExpect(jsonPath("$.data.carts[0].description", equalTo(cartDto.getCarts().get(0).getDescription())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].productId", equalTo(cartDto.getCarts().get(0).getCatalogs().get(0).getProductId())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].productName", equalTo(cartDto.getCarts().get(0).getCatalogs().get(0).getProductName())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].qty", equalTo(cartDto.getCarts().get(0).getCatalogs().get(0).getQty())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].unitPrice", equalTo(cartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].productId", equalTo(cartDto.getCarts().get(0).getCatalogs().get(1).getProductId())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].productName", equalTo(cartDto.getCarts().get(0).getCatalogs().get(1).getProductName())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].qty", equalTo(cartDto.getCarts().get(0).getCatalogs().get(1).getQty())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].unitPrice", equalTo(cartDto.getCarts().get(0).getCatalogs().get(1).getUnitPrice())));

        verify(queryRedisService, times(1)).getValues(any(String.class));

    }
}
