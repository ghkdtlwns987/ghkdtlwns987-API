package com.ghkdtlwns987.apiserver.Catalog.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.RequestCatalogDto;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Dto.ResponseDto;
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
import java.util.List;
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
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );
            String jsonResponse = responseEntity.getBody();
            ResultResponse resultResponse = objectMapper.readValue(
                    jsonResponse,
                    ResultResponse.class
            );
            Object data = resultResponse.getData();
            return Optional.ofNullable(data)
                    .map(d -> objectMapper.convertValue(d, ResponseCatalogDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response to ResponseOrderDto"));
        } catch (HttpClientErrorException e){
            log.error("", e);

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new ClientException(ErrorCode.PRODUCT_ID_ALREADY_EXISTS, "ProductId Already Exists");
            }
            throw new ServerException(
                    ErrorCode.INTERNAL_SERVER_ERROR.getCode()
            );
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}