package com.ghkdtlwns987.apiserver.Order.Exception;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class OutOfStockException extends BuisinessException {
    public OutOfStockException(){
        super(ErrorCode.OUT_OF_STOCK);
    }
}
