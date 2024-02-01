package com.ghkdtlwns987.apiserver.Catalog.Service.Inter;

import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;

import java.util.List;

public interface QueryCatalogService {
    /**
     * 상품 전체를 조회하는 기능입니다.
     * @return
     */
    List<ResponseCatalogDto> getAllCatalogs();

    /**
     * productId를 기반으로 Catalog를 조회하는 기능입니다.
     * @param productId
     * @return ResponseCatalogDto
     */
    ResponseCatalogDto getCatalogsByProductId(String productId);

    /**
     * ProductName을 기반으로 Catalog를 조회하는 기능입니다.
     * @param productName
     * @return ResponseCatalogDto
     */
    List<ResponseCatalogDto> getCatalogsByProductName(String productName);
}
