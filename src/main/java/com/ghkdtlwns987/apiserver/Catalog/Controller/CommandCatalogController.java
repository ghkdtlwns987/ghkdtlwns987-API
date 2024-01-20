package com.ghkdtlwns987.apiserver.Catalog.Controller;

import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Service.Inter.CommandCatalogService;
import com.ghkdtlwns987.apiserver.Global.ResultCode;
import com.ghkdtlwns987.apiserver.Member.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Order.Controller.CommandOrderController;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/catalog")
@RequiredArgsConstructor
public class CommandCatalogController {
    private final CommandCatalogService commandCatalogService;
    @GetMapping("/category")
    public EntityModel<ResultResponse> getAllCatalogs(){
        List<ResponseCatalogDto> response = commandCatalogService.getAllCatalogs();
        ResultResponse resultResponse = ResultResponse.of(ResultCode.GET_ALL_CATALOG_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(CommandOrderController.class).withSelfRel());

        return entityModel;
    }
}
