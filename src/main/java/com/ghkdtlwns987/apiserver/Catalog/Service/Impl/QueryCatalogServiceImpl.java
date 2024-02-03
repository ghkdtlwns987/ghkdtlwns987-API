package com.ghkdtlwns987.apiserver.Catalog.Service.Impl;

import com.ghkdtlwns987.apiserver.Catalog.Command.CommandCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Command.QueryCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Service.Inter.QueryCatalogService;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueryCatalogServiceImpl implements QueryCatalogService {
    private final QueryCatalog queryCatalog;
    @Override
    public List<ResponseCatalogDto> getAllCatalogs() {
        return queryCatalog.getAllCatalogRequest();
    }

    @Override
    public ResponseCatalogDto getCatalogsByProductId(String productId) throws ServerException {
        return queryCatalog.getCategoriesByProductIdRequest(productId);
    }

    @Override
    public List<ResponseCatalogDto> getCatalogsByProductName(String productName) {
        return queryCatalog.getCategoriesByProductNameRequest(productName);
    }
}
