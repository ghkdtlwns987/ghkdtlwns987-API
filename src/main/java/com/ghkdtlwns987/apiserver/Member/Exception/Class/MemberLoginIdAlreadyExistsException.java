package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;

public class MemberLoginIdAlreadyExistsException extends BuisinessException {
    public MemberLoginIdAlreadyExistsException(){
        super(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
    }
}
