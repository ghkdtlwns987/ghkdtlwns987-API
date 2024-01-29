package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class MemberLoginIdNotExistsException extends BuisinessException {
    public MemberLoginIdNotExistsException(){
        super(ErrorCode.MEMBER_LOGINID_NOT_EXISTS);
    }
}
