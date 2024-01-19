package com.ghkdtlwns987.apiserver.Catalog.Command;

import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CatalogCommand {
    private final CatalogConfig catalogConfig;
    private final RestTemplate restTemplate;

    public List<ResponseCatalogDto> getAllCatalogRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs")
                .build()
                .toUri();

        ResponseEntity<List<ResponseCatalogDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ResponseCatalogDto>>() {}
        );

        List<ResponseCatalogDto> result = response.getBody().stream()
                .collect(Collectors.toList());

        return result;
    }
}
