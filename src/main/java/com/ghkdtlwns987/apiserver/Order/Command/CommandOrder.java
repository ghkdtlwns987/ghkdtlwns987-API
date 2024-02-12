package com.ghkdtlwns987.apiserver.Order.Command;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultListResponse;
import com.ghkdtlwns987.apiserver.Global.Dto.ResultResponse;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Order.Config.OrderConfig;
import com.ghkdtlwns987.apiserver.Order.Dto.RequestOrderDto;
import com.ghkdtlwns987.apiserver.Order.Dto.ResponseOrderDto;
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
public class CommandOrder {
    private final OrderConfig orderConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ResponseOrderDto createOrderRequest(String userId, RequestOrderDto request) throws ServerException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RequestOrderDto> requestEntity = new HttpEntity<>(request, headers);

        URI uri = UriComponentsBuilder
                .fromUriString(orderConfig.getOrderUrl())
                .path("/order/" + userId + "/orders")
                .build()
                .toUri();

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
                    .map(d -> objectMapper.convertValue(d, ResponseOrderDto.class))
                    .orElseThrow(() -> new RuntimeException("Failed to map JSON response to ResponseOrderDto"));
        }
        catch (HttpClientErrorException e){
            log.error(e.getMessage());
            if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
                if(e.getMessage().equals(ErrorCode.PRODUCT_ID_NOT_EXISTS.getMessage())){
                    throw new ClientException(ErrorCode.PRODUCT_ID_NOT_EXISTS, "존재하지 않는 ProductId 입니다.");
                }
                if(e.getMessage().equals(ErrorCode.OUT_OF_STOCK.getMessage())){
                    throw new ClientException(ErrorCode.OUT_OF_STOCK, "재고가 모두 소진되었습니다.");
                }
            }
            throw new ServerException(
                    ErrorCode.INTERNAL_SERVER_ERROR.getCode()
            );
        }
        catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
