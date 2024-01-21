package com.ghkdtlwns987.apiserver.Global.Dto;

import com.ghkdtlwns987.apiserver.Global.Config.ResultCode;
import lombok.Getter;

@Getter
public class ResultResponse {
    private int status;
    private String code;
    private String message;
    private Object data;
    public static ResultResponse of(ResultCode resultCode, Object data){
        return new ResultResponse(resultCode, data);
    }

    public ResultResponse(ResultCode resultCode, Object data){
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
}