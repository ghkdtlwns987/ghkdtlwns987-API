package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Exception.BuisinessException;

public class MemberAlreadyExistsException extends BuisinessException {
    public MemberAlreadyExistsException(){
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
