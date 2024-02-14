package com.ghkdtlwns987.apiserver.Member.AOP;

import com.ghkdtlwns987.apiserver.Member.Dto.MemberCreateRequestDto;
import com.ghkdtlwns987.apiserver.Member.Dto.MemberUpdatePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
@RequiredArgsConstructor
public class CommandMemberServicePasswordEncryption {
    private final PasswordEncoder passwordEncoder;
    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.signup(..)) " + "&& args(memberCreateRequestDto)")
    private void signupPasswordEncryption(MemberCreateRequestDto memberCreateRequestDto) {
        memberCreateRequestDto.setPassword(passwordEncoder.encode(memberCreateRequestDto.getPassword()));
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.updatePassword(..)) " + "&& args(loginId, memberUpdatePasswordRequestDto)")
    private void updatePasswordEncryption(String loginId, MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto) {
        memberUpdatePasswordRequestDto.setNewPassword(passwordEncoder.encode(memberUpdatePasswordRequestDto.getNewPassword()));
    }
}
