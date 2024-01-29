package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class MemberNicknameNotExistsException extends BuisinessException {
    public MemberNicknameNotExistsException(){
        super(ErrorCode.MEMBER_NICKNAME_NOT_EXISTS);
    }

}
