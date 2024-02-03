package com.ghkdtlwns987.apiserver.Catalog.Controller;

import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Service.Inter.QueryCatalogService;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class QueryCatalogController {
    private final QueryCatalogService queryCatalogService;
    @GetMapping("/catalogs")
    public EntityModel<ResultResponse> getAllCatalogs(){
        List<ResponseCatalogDto> response = queryCatalogService.getAllCatalogs();
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_ALL_CATALOG_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(QueryCatalogController.class).withSelfRel());

        return entityModel;
    }

    @GetMapping("/catalog/{productName}")
    public EntityModel<ResultResponse> getCatalogsByProductName(@PathVariable String productName){
        List<ResponseCatalogDto> response = queryCatalogService.getCatalogsByProductName(productName);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_CATALOG_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(QueryCatalogController.class).withSelfRel());
        return entityModel;
    }

    @GetMapping("/catalog/Id/{productId}")
    public EntityModel<ResultResponse> getCatalogsByProductId(@PathVariable String productId) throws ServerException {
        ResponseCatalogDto response = queryCatalogService.getCatalogsByProductId(productId);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_CATALOG_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(QueryCatalogController.class).withSelfRel());
        return entityModel;
    }
}
