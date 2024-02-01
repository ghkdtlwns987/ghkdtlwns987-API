package com.ghkdtlwns987.apiserver.Order.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Order.Config.OrderConfig;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class QueryOrder {
    private final OrderConfig orderConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<ResponseOrderDto> getOrderDataRequest(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(orderConfig.getOrderUrl())
                .path("/order/" + userId + "/orders")
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String jsonResponse = responseEntity.getBody();

        try {
            ResultListResponse<ResponseOrderDto> resultListResponse = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<ResultListResponse<ResponseOrderDto>>() {}
            );
            System.out.println("Received Response: " + resultListResponse);

            return resultListResponse.getData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}