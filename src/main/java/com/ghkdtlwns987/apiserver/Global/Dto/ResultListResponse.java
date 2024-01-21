package com.ghkdtlwns987.apiserver.Global.Dto;

import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import lombok.Getter;

import java.util.List;

public class ResultListResponse<T> {
    private int status;
    private String code;
    private String message;
    private List<T> data;  // 데이터 필드를 List<T>로 변경

    public static <T> ResultListResponse<T> of(ResultCode resultCode, List<T> data) {
        return new ResultListResponse<>(resultCode, data);
    }

    public ResultListResponse(ResultCode resultCode, List<T> data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    // getter, setter 등 필요한 메서드들...
}