package com.ghkdtlwns987.apiserver.Member.Service.Impl;

import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandMemberServiceImpl implements CommandMemberService {
    private final QueryMemberService queryMemberService;
    private final CommandMemberRepository commandMemberRepository;
    @Override
    @Transactional
    public MemberCreateResponseDto signup(MemberCreateRequestDto memberCreateRequestDto){
        Member member = memberCreateRequestDto.toEntity();
        Member savedMember = commandMemberRepository.save(member);
        return MemberCreateResponseDto.fromEntity(savedMember);
    }

    @Override
    @Transactional
    public MemberUpdatePasswordResponseDto updatePassword(String loginId, MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto) {
        Member member = queryMemberService.findMemberByLoginId(loginId);
        member.updateMemberPassword(memberUpdatePasswordRequestDto.getNewPassword());
        return MemberUpdatePasswordResponseDto.fromEntity(member);
    }

    @Override
    @Transactional
    public MemberUpdateNicknameResponseDto updateNickname(String loginId, String newNickname) {
        Member member = queryMemberService.findMemberByLoginId(loginId);
        member.updateMemberNickname(newNickname);
        return MemberUpdateNicknameResponseDto.fromEntity(member);
    }

    @Override
    @Transactional
    public MemberWithdrawalResponseDto witrawalMember(String loginId) {
        Member member = queryMemberService.findMemberByLoginId(loginId);
        member.withdrawMember();
        return MemberWithdrawalResponseDto.fromEntity(member);
    }

    @Override
    @Transactional(readOnly = true)
    public MemberGetInformationResponseDto getMemberInfo(String loginId) {
        Member member = queryMemberService.findMemberByLoginId(loginId);
        return MemberGetInformationResponseDto.fromEntity(member);
    }
}
