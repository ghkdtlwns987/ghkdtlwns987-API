package com.ghkdtlwns987.apiserver.Order.Controller;

import com.ghkdtlwns987.apiserver.Global.Dto.ErrorResponseDto;
import com.ghkdtlwns987.apiserver.Order.Exception.OutOfStockException;
import com.ghkdtlwns987.apiserver.Order.Exception.ProductIdNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommandOrderControllerAdvice {
    @ExceptionHandler({
            OutOfStockException.class,
            ProductIdNotExistsException.class
    })
    public ResponseEntity<ErrorResponseDto> handleProductException(Exception ex) {
        log.error("[BAD_REQUEST] handleProductException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
