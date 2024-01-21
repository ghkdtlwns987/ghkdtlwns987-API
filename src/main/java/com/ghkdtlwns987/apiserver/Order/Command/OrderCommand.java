package com.ghkdtlwns987.apiserver.Order.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Order.Config.OrderConfig;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
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
public class OrderCommand {
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
            // TypeReference를 사용하여 JSON 문자열을 객체로 변환
            ResultListResponse<ResponseOrderDto> resultListResponse = objectMapper.readValue(
                    jsonResponse,
                    new TypeReference<ResultListResponse<ResponseOrderDto>>() {}
            );
            System.out.println("Received Response: " + resultListResponse);

            return resultListResponse.getData(); // 데이터만 반환하도록 수정
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseOrderDto createOrderRequest(String userId, RequestOrderDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestOrderDto> requestEntity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder
                .fromUriString(orderConfig.getOrderUrl())
                .path("/order/" + userId + "/orders")
                .build()
                .toUri();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String jsonResponse = responseEntity.getBody();

        try {
            ResultResponse resultResponse = objectMapper.readValue(
                    jsonResponse,
                    ResultResponse.class
            );

            Object data = resultResponse.getData();

            return Optional.ofNullable(data)
                    .map(d -> objectMapper.convertValue(d, ResponseOrderDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response to ResponseOrderDto"));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON response", e);
            return null;
        }
    }

}
