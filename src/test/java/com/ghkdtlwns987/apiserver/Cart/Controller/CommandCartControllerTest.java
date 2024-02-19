package com.ghkdtlwns987.apiserver.Cart.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Cart.Dto.CartDto;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.CommandRedisService;
import com.ghkdtlwns987.apiserver.Cart.Service.Inter.QueryRedisService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Duration;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandCartController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandCartControllerTest {

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

    @MockBean
    CommandRedisService commandRedisService;
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
    void 장바구니_등록_성공() throws Exception {
        when(commandRedisService.setValues(any(String.class), any(CartDto.class), any(Duration.class))).thenReturn(cartDto);

        ResultActions perform = mockMvc.perform(post("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cartDto))
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CART_CREATE_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CART_CREATE_SUCCESS.getMessage())))
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
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].unitPrice", equalTo(cartDto.getCarts().get(0).getCatalogs().get(1).getUnitPrice())))
        ;

        verify(commandRedisService, times(1)).setValues(any(String.class), any(CartDto.class), any(Duration.class));
    }

    /**
     * {
     *     "status": 200,
     *     "code": "R004",
     *     "message": "장바구니 삭제가 완료되었습니다.",
     *     "data": "데이터가 삭제 되었습니다."
     * }
     * @throws Exception
     */
    @Test
    void 장바구니_삭제_성공() throws Exception {
        doNothing().when(commandRedisService).deleteValues(any(String.class));

        ResultActions perform = mockMvc.perform(delete("/api/v1/cart?key=" + userId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CART_DELETE_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CART_DELETE_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", equalTo("데이터가 삭제 되었습니다.")));

        verify(commandRedisService, times(1)).deleteValues(any(String.class));
    }

    @Test
    void 장바구니_수정_성공() throws Exception {
        catalogs1 = CartDto.Catalogs.builder()
                .productId("updateProductId1")
                .productName("updateProductName1")
                .qty(qty1)
                .unitPrice(unitPrice1)
                .build();

        catalogs2 = CartDto.Catalogs.builder()
                .productId("updateProductId2")
                .productName("updateProductName2")
                .qty(qty2)
                .unitPrice(unitPrice2)
                .build();

        catalogsList = List.of(catalogs1, catalogs2);
        catalog = new CartDto.Catalog(name, description, catalogsList);

        CartDto updateCartDto = new CartDto(userId, List.of(catalog));

        when(commandRedisService.setValues(any(String.class), any(CartDto.class), any(Duration.class))).thenReturn(updateCartDto);

        ResultActions perform = mockMvc.perform(put("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateCartDto))
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.CART_UPDATE_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.CART_UPDATE_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.userId", equalTo(updateCartDto.getUserId())))
                .andExpect(jsonPath("$.data.carts[0].name", equalTo(updateCartDto.getCarts().get(0).getName())))
                .andExpect(jsonPath("$.data.carts[0].description", equalTo(updateCartDto.getCarts().get(0).getDescription())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].productId", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(0).getProductId())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].productName", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(0).getProductName())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].qty", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(0).getQty())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[0].unitPrice", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(0).getUnitPrice())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].productId", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(1).getProductId())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].productName", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(1).getProductName())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].qty", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(1).getQty())))
                .andExpect(jsonPath("$.data.carts[0].catalogs[1].unitPrice", equalTo(updateCartDto.getCarts().get(0).getCatalogs().get(1).getUnitPrice())))
        ;

        verify(commandRedisService, times(1)).setValues(any(String.class), any(CartDto.class), any(Duration.class));
    }
}
