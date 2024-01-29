package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class MemberPhomeAlreadyExistsException extends BuisinessException {
    public MemberPhomeAlreadyExistsException(){
        super(ErrorCode.MEMBER_PHONE_NOT_EXISTS);
    }
}
