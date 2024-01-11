package com.ghkdtlwns987.apiserver.Member.Service.Impl;

import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberAlreadyExistsException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberAlreadyWithdrawedException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberLoginIdNotExistsException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberNicknameAlreadyExistsException;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandMemberServiceImpl implements CommandMemberService {
    private final QueryMemberService queryMemberService;
    private final CommandMemberRepository commandMemberRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public MemberCreateResponseDto signup(MemberCreateRequestDto memberCreateRequestDto) throws Exception{
        if(checkMemberValidation(memberCreateRequestDto)){
            throw new MemberAlreadyExistsException();
        }

        memberCreateRequestDto.setPassword(passwordEncoder.encode(memberCreateRequestDto.getPassword()));
        Member member = memberCreateRequestDto.toEntity();
        if(isWithdrawd(member)){
            throw new MemberAlreadyWithdrawedException();
        }

        Member savedMember = commandMemberRepository.save(member);
        return MemberCreateResponseDto.fromEntity(savedMember);
    }


    @Override
    @Transactional
    public MemberUpdatePasswordResponseDto updatePassword(String loginId, String newPassword) throws Exception{
        if(checkMemberLoginIdNotExists(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        Member member = queryMemberService.findMemberByLoginId(loginId);

        if(isWithdrawd(member)){
            throw new MemberAlreadyWithdrawedException();
        }

        member.updateMemberPassword(passwordEncoder.encode(newPassword));
        return MemberUpdatePasswordResponseDto.fromEntity(member);

    }

    @Override
    @Transactional
    public MemberUpdateNicknameResponseDto updateNickname(String loginId, String newNickname) throws Exception{
        if(checkMemberLoginIdNotExists(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        if(checkMemberNicknameExists(newNickname)){
            throw new MemberNicknameAlreadyExistsException();
        }

        Member member = queryMemberService.findMemberByLoginId(loginId);
        if(isWithdrawd(member)){
            throw new MemberAlreadyWithdrawedException();
        }

        member.updateMemberNickname(newNickname);
        return MemberUpdateNicknameResponseDto.fromEntity(member);
    }

    @Override
    @Transactional
    public MemberWithdrawalResponseDto witrawalMember(String loginId) throws Exception {
        if(checkMemberLoginIdNotExists(loginId)){
            throw new MemberLoginIdNotExistsException();
        }

        Member member = queryMemberService.findMemberByLoginId(loginId);
        if(isWithdrawd(member)){
            throw new MemberAlreadyWithdrawedException();
        }

        member.withdrawMember();
        return MemberWithdrawalResponseDto.fromEntity(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberGetInformationResponseDto getMemberInfo(String loginId) throws Exception {
        if(checkMemberLoginIdNotExists(loginId)){
            throw new MemberLoginIdNotExistsException();
        }
        Member member = queryMemberService.findMemberByLoginId(loginId);
        if(isWithdrawd(member)){
            throw new MemberAlreadyWithdrawedException();
        }

        return MemberGetInformationResponseDto.fromEntity(member);
    }

    private boolean isWithdrawd(Member member) throws Exception{
        if(member.isWithdraw()){
            return true;
        }
        return false;
    }
    private boolean checkMemberLoginIdNotExists(String loginId) throws Exception{
        if(queryMemberService.memberExistsByLoginId(loginId)){
            return false;
        }
        return true;
    }
    private boolean checkMemberLoginIdExists(String loginId) throws Exception{
        if(queryMemberService.memberExistsByLoginId(loginId)){
            return true;
        }
        return false;
    }

    private boolean checkMemberNicknameExists(String changeNickname) throws Exception{
        if(queryMemberService.memberExistsByNickname(changeNickname)){
            return true;
        }
        return false;
    }
    private boolean checkMemberValidation(MemberCreateRequestDto memberCreateRequestDto) throws Exception{
        if(queryMemberService.memberExistsByLoginId(memberCreateRequestDto.getLoginId())){
            return true;
        }
        if(queryMemberService.memberExistsByNickname(memberCreateRequestDto.getNickname())){
            return true;
        }
        if(queryMemberService.memberExistsByEmail(memberCreateRequestDto.getEmail())){
            return true;
        }
        return false;
    }
}
