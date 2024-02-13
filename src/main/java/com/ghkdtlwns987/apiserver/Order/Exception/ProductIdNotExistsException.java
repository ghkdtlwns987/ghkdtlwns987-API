package com.ghkdtlwns987.apiserver.Order.Exception;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class ProductIdNotExistsException extends BuisinessException {
    public ProductIdNotExistsException(){
        super(ErrorCode.PRODUCT_ID_NOT_EXISTS);
    }
}