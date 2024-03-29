package com.ghkdtlwns987.apiserver.Catalog.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Catalog.Config.CatalogConfig;
import com.ghkdtlwns987.apiserver.Catalog.Dto.ResponseCatalogDto;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
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

    public ResponseCatalogDto getCategoriesByProductIdRequest(String productId) throws ServerException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(catalogConfig.getCatalogUrl())
                .path("/catalog/catalogs/search/Id/" + productId)
                .build()
                .toUri();

        log.info("uri: " + uri);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            // REST 호출로부터 받은 응답이 null인지 확인
            if (response == null || response.getBody() == null) {
                throw new ServerException(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
            }

            String jsonResponse = response.getBody();

            ResultResponse resultResponse = objectMapper.readValue(
                    jsonResponse,
                    ResultResponse.class
            );

            return Optional.ofNullable(resultResponse.getData())
                    .map(d -> objectMapper.convertValue(d, ResponseCatalogDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response to ResponseCatalogDto"));
        } catch (HttpClientErrorException e){
            log.error("", e);

            if (e.getStatusCode().equals(HttpStatus.BAD_REQUEST)) {
                throw new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, "존재하지 않는 ProductId 입니다.");
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