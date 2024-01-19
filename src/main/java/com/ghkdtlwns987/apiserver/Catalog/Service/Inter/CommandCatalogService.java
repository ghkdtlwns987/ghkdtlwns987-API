package com.ghkdtlwns987.apiserver.Catalog.Service.Inter;

import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;

import java.util.List;

public interface CommandCatalogService {
    /**
     * 상품 전체를 조회하는 기느입니다.
     * @return
     */
    List<ResponseCatalogDto> getAllCatalogs();
}
