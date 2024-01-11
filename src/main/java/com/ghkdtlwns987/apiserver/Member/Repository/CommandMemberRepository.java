package com.ghkdtlwns987.apiserver.Member.Repository;

import com.ghkdtlwns987.apiserver.Member.Dto.MemberCreateRequestDto;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CommandMemberRepository{
    /**
     * 회원 데이터를 저장합니다.
     * @param member
     * @return Member
     * @author 황시준
     * @since  1.0
     */
    Member save(Member member);
}
