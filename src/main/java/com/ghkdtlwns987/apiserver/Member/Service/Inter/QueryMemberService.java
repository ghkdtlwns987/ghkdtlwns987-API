package com.ghkdtlwns987.apiserver.Member.Service.Inter;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;

public interface QueryMemberService {
    /**
     * Id로 회원을 검색하는 기능입니다.
     * @param Id
     * @return Member
     */
    Member findById(Long Id);

    /**
     * loginId 로 회원을 검색합니다.
     * @param loginId
     * @return Member
     */
    Member findMemberByLoginId(String loginId);

}
