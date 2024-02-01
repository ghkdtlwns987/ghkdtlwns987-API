package com.ghkdtlwns987.apiserver.Catalog.Controller;

import com.ghkdtlwns987.apiserver.Catalog.Command.CommandCatalog;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/v1/member")
@RequiredArgsConstructor
public class CommandCatalogController {
    private final CommandCatalog commandCatalog;
    @PostMapping("/catalog")
    public EntityModel<ResultResponse> createCatalogs(@RequestBody RequestCatalogDto requestCatalogDto){
        ResponseCatalogDto response = commandCatalog.createCatalogRequest(requestCatalogDto);
        ResultResponse resultResponse = ResultResponse.of(ResultCode.CREATE_CATALOG_REQUEST_SUCCESS, response);

        EntityModel<ResultResponse> entityModel = EntityModel.of(resultResponse);
        entityModel.add(linkTo(QueryCatalogController.class).withSelfRel());

        return entityModel;
    }
}
