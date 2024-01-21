package com.ghkdtlwns987.apiserver.Member.Exception.Class;

import com.ghkdtlwns987.apiserver.Global.Exception.BuisinessException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;

public class MemberAlreadyWithdrawedException extends BuisinessException {
    public MemberAlreadyWithdrawedException() {
        super(ErrorCode.MEMBER_ALREADY_WITHDRAW);
    }
}
