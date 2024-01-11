package com.ghkdtlwns987.apiserver.Member.Exception;

import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ErrorException extends RuntimeException{
    private final ErrorCode errorCode;
}
