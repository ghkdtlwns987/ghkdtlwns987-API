package com.ghkdtlwns987.apiserver.Member.AOP;

import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Dto.MemberCreateRequestDto;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.*;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
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
            throw new ClientException(ErrorCode.MEMBER_LOGINID_ALREADY_EXISTS, "이미 가입된 LoginId 입니다.");

        }
        if(queryMemberRepository.existsMemberByNickname(memberCreateRequestDto.getNickname())){
            throw new ClientException(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS, "이미 가입된 Nickname 입니다.");

        }
        if(queryMemberRepository.existsMemberByEmail(memberCreateRequestDto.getEmail())){
            throw new ClientException(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS, "이미 가입된 Email 입니다.");

        }
        if(queryMemberRepository.existsMemberByPhone(memberCreateRequestDto.getPhone())){
            throw new ClientException(ErrorCode.MEMBER_PHONE_ALREADY_EXISTS, "이미 가입된 Phone Number 입니다.");
        }
        if(queryMemberRepository.isWithdraw()){
            throw new ClientException(ErrorCode.MEMBER_ALREADY_WITHDRAW, "이미 회원 탈퇴 처리된 회원입니다.");
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.updateNickname(..)) " + "&& args(loginId, newNickname)")
    private void updateNicknameValidation(String loginId, String newNickname){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "존재하지 않는 LoginId 입니다.");
        }
        if(queryMemberRepository.existsMemberByNickname(newNickname)){
            throw new ClientException(ErrorCode.MEMBER_NICKNAME_NOT_EXISTS, "존재하지 않는 Nickname 입니다.");
        }
        if(queryMemberRepository.isWithdraw()){
            throw new ClientException(ErrorCode.MEMBER_ALREADY_WITHDRAW, "이미 회원 탈퇴 처리된 회원입니다.");
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.updatePassword(..)) " + "&& args(loginId, newPassword)")
    private void updatePasswordValidation(String loginId, String newPassword){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "존재하지 않는 LoginId 입니다.");
        }
        if(queryMemberRepository.isWithdraw()){
            throw new ClientException(ErrorCode.MEMBER_ALREADY_WITHDRAW, "이미 회원 탈퇴 처리된 회원입니다.");
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.witrawalMember(..)) " + "&& args(loginId)")
    private void withdrawalMemberValidation(String loginId){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "존재하지 않는 LoginId 입니다.");
        }
        if(queryMemberRepository.isWithdraw()){
            throw new ClientException(ErrorCode.MEMBER_ALREADY_WITHDRAW, "이미 회원 탈퇴 처리된 회원입니다.");
        }
    }

    @Before("execution(* com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService.getMemberInfo(..)) " + "&& args(loginId)")
    private void getMemberInfoValidation(String loginId){
        if(!queryMemberRepository.existsMemberByLoginId(loginId)){
            throw new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "존재하지 않는 LoginId 입니다.");
        }
        if(queryMemberRepository.isWithdraw()){
            throw new ClientException(ErrorCode.MEMBER_ALREADY_WITHDRAW, "이미 회원 탈퇴 처리된 회원입니다.");
        }
    }

}
