package com.ghkdtlwns987.apiserver.Catalog.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.rmi.ServerException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommandCatalog {
    private final CatalogConfig catalogConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    public ResponseCatalogDto createCatalogRequest(RequestCatalogDto request) throws ServerException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestCatalogDto> requestEntity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs")
                .build()
                .toUri();

        log.info("uri: " + uri);


        try {
            ResponseEntity<ResultResponse> responseEntity = restTemplate.postForEntity(
                    uri,
                    requestEntity,
                    ResultResponse.class
            );

            ResultResponse resultResponse = responseEntity.getBody();

            if (resultResponse == null || resultResponse.getData() == null) {
                throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
            }

            return objectMapper.convertValue(resultResponse.getData(), ResponseCatalogDto.class);
        } catch (HttpClientErrorException e) {
            log.error("", e);

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new ClientException(ErrorCode.PRODUCT_ID_ALREADY_EXISTS, "ProductId 가 이미 존재합니다.");
            }
            throw new ServerException(
                    ErrorCode.INTERNAL_SERVER_ERROR.getCode()
            );
        }
    }

}