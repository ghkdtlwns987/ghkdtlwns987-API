package com.ghkdtlwns987.apiserver.Catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Command.CommandCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Command.QueryCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Controller.CommandCatalogController;
import com.ghkdtlwns987.apiserver.Catalog.Controller.QueryCatalogController;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Service.Inter.QueryCatalogService;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QueryCatalogController.class)
@AutoConfigureMockMvc(addFilters = false)
public class QueryProductsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;


    @MockBean
    QueryCatalogService queryCatalogService;

    RequestCatalogDto requestCatalogDto;
    ResponseCatalogDto responseCatalogDto;
    ResponseCatalogDto responseCatalogDto2;
    ResponseCatalogDto responseCatalogDto3;


    private String productId = "CATALOG-0001";
    private String productId2 = "CATALOG-0002";
    private String productId3 = "CATALOG-0003";
    private String productName = "Berlin";

    private Integer qty = 100;
    private Integer unitPrice =1000;
    private String orderId = "f932204e-577e-4d17-9101-dd870b7416dd";
    private String userId = "1561d7eb-ab64-48a1-95c8-80d1602bd826i";

    List<ResponseCatalogDto> responseCatalogDtoList;
    @BeforeEach
    void setUp() {
        requestCatalogDto = new RequestCatalogDto(productId, productName, qty, unitPrice, orderId, userId);
        responseCatalogDto = new ResponseCatalogDto(productId, productName, qty, unitPrice);
        responseCatalogDto2 = new ResponseCatalogDto(productId2, productName, qty, unitPrice);
        responseCatalogDto3 = new ResponseCatalogDto(productId3, productName, qty, unitPrice);

        responseCatalogDtoList = Arrays.asList(responseCatalogDto, responseCatalogDto2, responseCatalogDto3);

    }
    /**
     * {
     *     "message": "ProductId 가 이미 존재합니다."
     * }
     */
    @Test
    void 존재하지_않는_productId로_조회() throws Exception {
        when(queryCatalogService.getCatalogsByProductId(any(String.class))).thenThrow(
                new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, "존재하지 않는 ProductId 입니다.")
        );

        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/Id/CATALOG-0001231")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("존재하지 않는 ProductId 입니다.")));
    }

    /**
     * {
     *     "status": 200,
     *     "code": "C001",
     *     "message": "상품 조회가 완료되었습니다.",
     *     "data": [],
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     * @throws Exception
     */
    @Test
    void 존재하지_않는_productName으로_조회_빈배열_반환() throws Exception{
        when(queryCatalogService.getCatalogsByProductName(any(String.class)))
                .thenReturn(Collections.emptyList());

        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/Berlin3")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", equalTo(Collections.emptyList())))
        ;
    }

    @Test
    void 전체상품_조회_상품이_존재하지_않는_경우() throws Exception{
        when(queryCatalogService.getAllCatalogs()).thenReturn(Collections.emptyList());

        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_ALL_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data", equalTo(Collections.emptyList())));
    }
    /**
     * {
     *     "status": 200,
     *     "code": "C001",
     *     "message": "상품 조회가 완료되었습니다.",
     *     "data": {
     *         "productId": "CATALOG-0001",
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
     * @throws Exception
     */
    @Test
    void productId로_조회_성공() throws Exception{
        when(queryCatalogService.getCatalogsByProductId(any(String.class))).thenReturn(responseCatalogDto);
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/Id/CATALOG-0001")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data.productId", equalTo(responseCatalogDto.getProductId())))
                .andExpect(jsonPath("$.data.productName", equalTo(responseCatalogDto.getProductName())))
                .andExpect(jsonPath("$.data.stock", equalTo(responseCatalogDto.getStock())))
                .andExpect(jsonPath("$.data.unitPrice", equalTo(responseCatalogDto.getUnitPrice())));
    }

    /**
     * {
     *     "status": 200,
     *     "code": "C001",
     *     "message": "상품 조회가 완료되었습니다.",
     *     "data": [
     *         {
     *             "productId": "CATALOG-0001",
     *             "productName": "Berlin",
     *             "stock": 100,
     *             "unitPrice": 1500
     *         },
     *         {
     *             "productId": "CATALOG-00062",
     *             "productName": "Berlin",
     *             "stock": 100,
     *             "unitPrice": 1500
     *         }
     *     ],
     *     "_links": {
     *         "self": {
     *             "href": "http://localhost:8000/api/v1/member"
     *         }
     *     }
     * }
     * @throws Exception
     */
    @Test
    void productName으로_조회_성공() throws Exception{
        when(queryCatalogService.getCatalogsByProductName(any(String.class))).thenReturn(responseCatalogDtoList);
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/Berlin")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data[0].productId", equalTo(responseCatalogDtoList.get(0).getProductId())))
                .andExpect(jsonPath("$.data[0].productName", equalTo(responseCatalogDtoList.get(0).getProductName())))
                .andExpect(jsonPath("$.data[0].stock", equalTo(responseCatalogDtoList.get(0).getStock())))
                .andExpect(jsonPath("$.data[0].unitPrice", equalTo(responseCatalogDtoList.get(0).getUnitPrice())))
                .andExpect(jsonPath("$.data[1].productId", equalTo(responseCatalogDtoList.get(1).getProductId())))
                .andExpect(jsonPath("$.data[1].productName", equalTo(responseCatalogDtoList.get(1).getProductName())))
                .andExpect(jsonPath("$.data[1].stock", equalTo(responseCatalogDtoList.get(1).getStock())))
                .andExpect(jsonPath("$.data[1].unitPrice", equalTo(responseCatalogDtoList.get(1).getUnitPrice())));
    }

    @Test
    void 전체_상품_조회() throws Exception{
        when(queryCatalogService.getAllCatalogs()).thenReturn(responseCatalogDtoList);
        ResultActions perform = mockMvc.perform(get("/api/v1/catalog/catalogs")
                .contentType(MediaType.APPLICATION_JSON)
        );

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo(ResultCode.GET_ALL_CATALOG_REQUEST_SUCCESS.getCode())))
                .andExpect(jsonPath("$.message", equalTo(ResultCode.GET_ALL_CATALOG_REQUEST_SUCCESS.getMessage())))
                .andExpect(jsonPath("$.data[0].productId", equalTo(responseCatalogDtoList.get(0).getProductId())))
                .andExpect(jsonPath("$.data[0].productName", equalTo(responseCatalogDtoList.get(0).getProductName())))
                .andExpect(jsonPath("$.data[0].stock", equalTo(responseCatalogDtoList.get(0).getStock())))
                .andExpect(jsonPath("$.data[0].unitPrice", equalTo(responseCatalogDtoList.get(0).getUnitPrice())))
                .andExpect(jsonPath("$.data[1].productId", equalTo(responseCatalogDtoList.get(1).getProductId())))
                .andExpect(jsonPath("$.data[1].productName", equalTo(responseCatalogDtoList.get(1).getProductName())))
                .andExpect(jsonPath("$.data[1].stock", equalTo(responseCatalogDtoList.get(1).getStock())))
                .andExpect(jsonPath("$.data[1].unitPrice", equalTo(responseCatalogDtoList.get(1).getUnitPrice())));
    }
}
