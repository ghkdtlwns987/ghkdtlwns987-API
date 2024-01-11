package com.ghkdtlwns987.apiserver.Member.Persistent;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.QMember;
import com.ghkdtlwns987.apiserver.Member.Repository.QueryMemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@RequiredArgsConstructor
@Repository
public class QueryDslQueryMemberRepository implements QueryMemberRepository {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findById(Long Id) {
        QMember qMember = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qMember)
                .where(qMember.Id.eq(Id))
                .fetchFirst());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findMemberByEmail(String email) {
        QMember qMember = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qMember)
                .where(qMember.email.eq(email))
                .fetchFirst());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findMemberByLoginId(String loginId) {
        QMember qMember = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qMember)
                .where(qMember.loginId.eq(loginId))
                .fetchFirst());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> findMemberByPhone(String phone) {
        QMember qMember = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qMember)
                .where(qMember.phone.eq(phone))
                .fetchFirst());
    }


    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByEmail(String email) {
        QMember member = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchFirst()).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByLoginId(String loginId){
        QMember member = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.loginId.eq(loginId))
                .fetchFirst()).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsMemberByNickname(String nickname) {
        QMember member = QMember.member;
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.nickname.eq(nickname))
                .fetchFirst()).isPresent();

    }
}
