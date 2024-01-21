package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;

public class MemberNicknameAlreadyExistsException extends BuisinessException {
    public MemberNicknameAlreadyExistsException(){
        super(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }
}
