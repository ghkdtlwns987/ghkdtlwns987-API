package com.ghkdtlwns987.apiserver.Member.AOP;

import com.ghkdtlwns987.apiserver.Member.Dto.MemberCreateRequestDto;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.*;
import com.ghkdtlwns987.apiserver.Member.Repository.QueryMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
@RequiredArgsConstructor
public class CommandMemberServiceValidation {
    private final QueryMemberRepository queryMemberRepository;

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.signup(..)) " + "&& args(memberCreateRequestDto)")
    private void signupMemberValidation(MemberCreateRequestDto memberCreateRequestDto) {
        if(queryMemberRepository.existsMemberByLoginId(memberCreateRequestDto.getLoginId())){
            throw new MemberLoginIdAlreadyExistsException();
        }
        if(queryMemberRepository.existsMemberByNickname(memberCreateRequestDto.getNickname())){
            throw new MemberNicknameAlreadyExistsException();
        }
        if(queryMemberRepository.existsMemberByEmail(memberCreateRequestDto.getEmail())){
            throw new MemberEmailAlreadyExistsException();
        }
        if(queryMemberRepository.existsMemberByPhone(memberCreateRequestDto.getPhone())){
            throw new MemberPhomeAlreadyExistsException();
        }
        if(queryMemberRepository.isWithdraw()){
            throw new MemberAlreadyWithdrawedException();
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.updateNickname(..)) " + "&& args(loginId, newNickname)")
    private void updateNicknameValidation(String loginId, String newNickname){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        if(queryMemberRepository.existsMemberByNickname(newNickname)){
            throw new MemberNicknameAlreadyExistsException();
        }
        if(queryMemberRepository.isWithdraw()){
            throw new MemberAlreadyWithdrawedException();
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.updatePassword(..)) " + "&& args(loginId, newPassword)")
    private void updatePasswordValidation(String loginId, String newPassword){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        if(queryMemberRepository.isWithdraw()){
            throw new MemberAlreadyWithdrawedException();
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.witrawalMember(..)) " + "&& args(loginId)")
    private void withdrawalMemberValidation(String loginId){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        if(queryMemberRepository.isWithdraw()){
            throw new MemberAlreadyWithdrawedException();
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.getMemberInfo(..)) " + "&& args(loginId)")
    private void getMemberInfoValidation(String loginId){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        if(queryMemberRepository.isWithdraw()){
            throw new MemberAlreadyWithdrawedException();
        }
    }

}
