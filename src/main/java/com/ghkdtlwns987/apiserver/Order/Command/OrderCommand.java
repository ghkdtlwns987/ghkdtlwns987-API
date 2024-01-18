package com.ghkdtlwns987.apiserver.Order.Command;

import com.ghkdtlwns987.apiserver.Order.Config.OrderConfig;
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
public class OrderCommand {
    private final OrderConfig orderConfig;
    private final RestTemplate restTemplate;
    public List<ResponseOrderDto> getOrderData(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = UriComponentsBuilder
                .fromUriString(orderConfig.getOrderUrl())
                .path("/order/" + userId + "/orders")
                .build()
                .toUri();

        ResponseEntity<List<ResponseOrderDto>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<List<ResponseOrderDto>>() {}
        );

        List<ResponseOrderDto> result = response.getBody().stream()
                .collect(Collectors.toList());

        return result;
    }
}
