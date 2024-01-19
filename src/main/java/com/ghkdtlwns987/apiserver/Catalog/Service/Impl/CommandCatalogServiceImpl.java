package com.ghkdtlwns987.apiserver.Catalog.Service.Impl;

import com.ghkdtlwns987.apiserver.Catalog.Command.CatalogCommand;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Service.Inter.CommandCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandCatalogServiceImpl implements CommandCatalogService {
    private final CatalogCommand catalogCommand;
    @Override
    public List<ResponseCatalogDto> getAllCatalogs() {
        return catalogCommand.getAllCatalogRequest();
    }
}
