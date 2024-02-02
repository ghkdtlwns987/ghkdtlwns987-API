package com.ghkdtlwns987.apiserver.Catalog.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryCatalog {
    private final CatalogConfig catalogConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<ResponseCatalogDto> getAllCatalogRequest(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs")
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String jsonResponse = response.getBody();

        try {
            ResultListResponse<ResponseCatalogDto> resultListResponse = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<ResultListResponse<ResponseCatalogDto>>() {}
            );
            log.info("Received Response: " + resultListResponse);
            return resultListResponse.getData();
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    public ResponseCatalogDto getCategoriesByProductIdRequest(String productId){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs/search/Id/" + productId)
                .build()
                .toUri();

        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String jsonResponse = response.getBody();

        try {
            ResultResponse resultResponse = objectMapper.readValue(
                    jsonResponse,
                    ResultResponse.class
            );

            Object data = resultResponse.getData();

            return Optional.ofNullable(data)
                    .map(d -> objectMapper.convertValue(d, ResponseCatalogDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response to ResponseCatalogDto"));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON response", e);
            return null;
        }
    }

    public List<ResponseCatalogDto> getCategoriesByProductNameRequest(String productName){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs/search/" + productName)
                .build()
                .toUri();
        ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String jsonResponse = response.getBody();
        try {
            ResultListResponse<ResponseCatalogDto> resultListResponse = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<ResultListResponse<ResponseCatalogDto>>() {}
            );
            log.info("Received Response: " + resultListResponse);
            return resultListResponse.getData();
        }
        catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }
}