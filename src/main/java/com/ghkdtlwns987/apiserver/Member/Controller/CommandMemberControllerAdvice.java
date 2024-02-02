package com.ghkdtlwns987.apiserver.Member.Controller;

import com.ghkdtlwns987.apiserver.Global.Dto.ErrorResponseDto;
import com.ghkdtlwns987.apiserver.Global.Dto.ResponseDto;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;


@RestControllerAdvice
@Slf4j
public class CommandMemberControllerAdvice {
    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Object>> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ResponseDto.builder()
                        .success(false)
                        .status(HttpStatus.BAD_REQUEST)
                        .errorMessages(List.of(e.getMessage()))
                        .build()
        );
    }
    */
    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ResponseDto<Object>> handleClientException(ClientException e) {
        log.error("[{}] ClientException : {}", e.getResponseStatus(), e.getMessage());

        ResponseDto<Object> response = ResponseDto.builder()
                .success(false)
                .status(e.getResponseStatus())
                .errorMessages(
                        List.of(e.getErrorCode().getMessage()))
                .build();
        return ResponseEntity.status(e.getResponseStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex) {
        log.error("[INTERNAL_SERVER_ERROR] handleException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            HttpMessageNotReadableException.class,
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleValidationException(Exception ex) {
        log.error("[BAD_REQUEST] handleValidationException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            MemberLoginIdNotExistsException.class,
            MemberNicknameNotExistsException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleMemberNotExistsException(Exception ex) {
        log.error("[BAD_REQUEST] handleMemberNotExistsException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler({
            MemberEmailAlreadyExistsException.class,
            MemberLoginIdAlreadyExistsException.class,
            MemberNicknameAlreadyExistsException.class,
            MemberAlreadyWithdrawedException.class,
            MemberPhomeAlreadyExistsException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleMemberAlreadyExistsException(Exception ex) {
        log.error("[BAD_REQUEST] handleMemberAlreadyExistsException", ex);
        ErrorResponseDto error = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
