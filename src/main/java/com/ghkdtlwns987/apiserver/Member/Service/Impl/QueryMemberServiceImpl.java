package com.ghkdtlwns987.apiserver.Member.Service.Impl;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Repository.QueryMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class QueryMemberServiceImpl implements QueryMemberService {
    private final QueryMemberRepository queryMemberRepository;
    @Override
    @Transactional(readOnly = true)
    public Member findById(Long Id) {
        return queryMemberRepository.findById(Id)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_NOT_EXISTS,
                        "Member Not Found (Member Id) : " + Id
                ));
    }

    @Override
    public Member findMemberByLoginId(String loginId) {
        return queryMemberRepository.findMemberByLoginId(loginId)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_LOGINID_NOT_EXISTS,
                        "Member Not Found (Login Id) : " + loginId
                ));
    }
}
