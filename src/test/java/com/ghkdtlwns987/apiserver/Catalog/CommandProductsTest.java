package com.ghkdtlwns987.apiserver.Catalog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Command.CommandCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.rmi.ServerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CommandProductsTest {

    @Mock
    private CatalogConfig catalogConfig;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CommandCatalog commandCatalog;

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
        MockitoAnnotations.initMocks(this);
        requestCatalogDto = new RequestCatalogDto(productId, productName, qty, unitPrice, orderId, userId);
        responseCatalogDto = new ResponseCatalogDto(productId, productName, qty, unitPrice);
    }

    @Test
    void catalog_생성_테스트_이미_존재하는_ProductId() throws Exception {
        // Given
        when(catalogConfig.getCatalogUrl()).thenReturn("http://localhost:8002/api/v1/member/catalog/catalogs");
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST));

        // when then
        ClientException exception = assertThrows(ClientException.class, () -> commandCatalog.createCatalogRequest(requestCatalogDto));
        assertEquals(ErrorCode.PRODUCT_ID_ALREADY_EXISTS, exception.getErrorCode());
        assertEquals("ProductId 가 이미 존재합니다.", exception.getMessage());
    }

    @Test
    void catalog_생성_테스트_ServerException() throws Exception {
        // Given
        RequestCatalogDto requestCatalogDto = new RequestCatalogDto(productId, productName, qty, unitPrice, orderId, userId);
        when(catalogConfig.getCatalogUrl()).thenReturn("http://localhost:8002/api/v1/member/catalog/catalogs");
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        // When then
        ServerException exception = assertThrows(ServerException.class, () -> commandCatalog.createCatalogRequest(requestCatalogDto));
        assertEquals(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), exception.getMessage());
    }


    @Test
    void catalog_생성_테스트() throws Exception {
        ResultResponse resultResponse = new ResultResponse(ResultCode.CREATE_CATALOG_REQUEST_SUCCESS, responseCatalogDto);

        ResponseEntity<ResultResponse> responseEntity = ResponseEntity.ok()
                .body(resultResponse);

        // Mock 설정 수정
        when(catalogConfig.getCatalogUrl()).thenReturn("http://localhost:8002/api/v1/member/catalog/catalogs");
        when(restTemplate.postForEntity(any(URI.class), any(HttpEntity.class), any(Class.class)))
                .thenReturn(responseEntity);
        when(objectMapper.convertValue(resultResponse.getData(), ResponseCatalogDto.class)).thenReturn(responseCatalogDto);

        // When
        ResponseCatalogDto actualResponse = commandCatalog.createCatalogRequest(requestCatalogDto);

        // Then
        assertEquals(responseCatalogDto.getProductId(), actualResponse.getProductId());
        assertEquals(responseCatalogDto.getProductName(), actualResponse.getProductName());
        assertEquals(responseCatalogDto.getStock(), actualResponse.getStock());
        assertEquals(responseCatalogDto.getUnitPrice(), actualResponse.getUnitPrice());
    }
}