package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;

public class MemberEmailAlreadyExistsException extends BuisinessException {
    public MemberEmailAlreadyExistsException() {
        super(ErrorCode.MEMBER_EMAIL_NOT_EXISTS);
    }
}
