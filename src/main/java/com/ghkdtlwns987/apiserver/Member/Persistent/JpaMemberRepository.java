package com.ghkdtlwns987.apiserver.Member.Persistent;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import org.springframework.data.repository.Repository;

/**
 * 회원테이블에 JPA로 접근 가능하도록 작성한 인터페이스 입니다.
 * @author 황시준
 * @since  1.0
 */
public interface JpaMemberRepository extends Repository<Member, Long>, CommandMemberRepository {

}
