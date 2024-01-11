package com.ghkdtlwns987.apiserver.Repository;

import com.ghkdtlwns987.apiserver.IntegrationTest;
import com.ghkdtlwns987.apiserver.Member.Dto.MemberGetInformationResponseDto;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Persistent.QueryDslQueryMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
public class QueryDslMemberRepositoryTest extends IntegrationTest {

    private Long Id = 1L;
    private final String loginId = "ghkdtlwns987";
    private final String password = "testPassword";
    private final String email = "ghkdtlwns987@naver.com";
    private final String nickname = "testNickname";
    private final String userName = "황시준";
    private final String userId = "testUserId";
    private final String phone = "01048482771";
    private final Roles roles = Roles.USER;


    @Autowired
    QueryDslQueryMemberRepository queryMemberRepository;

    @Autowired
    CommandMemberRepository commandMemberRepository;

    @Autowired
    EntityManager entityManager;
    Member member;

    MemberGetInformationResponseDto memberGetInformationResponseDto;
    @BeforeEach
    void setUp() {
        member = Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .userId(userId)
                .phone(phone)
                .roles(roles)
                .build();

        entityManager.persist(member);

        memberGetInformationResponseDto = MemberGetInformationResponseDto.fromEntity(member);
    }

    // email, loginId, nickname
    @Test
    @DisplayName("회원 검증 로직 중 email이 존재하지 않을 때")
    void 회원_검증_로직_Email이_존재하지_않음() throws Exception{

        // when
        boolean result = queryMemberRepository.existsMemberByEmail("failedEmail@naver.com");

        // then
        assertThat(result).isEqualTo(false);

    }

    @Test
    @DisplayName("회원 검증 로직 중 loginId가 존재하지 않을 때")
    void 회원_검증_로직_loginId가_존재하지_않음() throws Exception{
        // when
        boolean result = queryMemberRepository.existsMemberByLoginId("failedLoginId");

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("회원 검증 로직 중 nickname이 존재하지 않을때")
    void 회원_검증_로직_nickname이_존재하지_않음() throws Exception{
        // when
        boolean result = queryMemberRepository.existsMemberByNickname("failedNickname");

        // then
        assertThat(result).isEqualTo(false);
    }

    @Test
    @DisplayName("회원 검색 실패(회원을 찾을 수 없음 - findById 테스트")
    void 회원을_찾을수_없음_findById() throws Exception {
        // when then
        assertThatThrownBy(() -> queryMemberRepository.findById(10L)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_NOT_EXISTS, "Member Id : " + (Id + 10L)
                )));
    }

    @Test
    @DisplayName("회원 검색 실패(회원을 찾을 수 없음 - findMemberByEmail 테스트")
    void 회원을_찾을수_없음_findByEmail() throws Exception {
        // when then
        assertThatThrownBy(() -> queryMemberRepository.findMemberByEmail("failedEmail@naver.com")
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_EMAIL_NOT_EXISTS, "Member Email : " + "failedEmail@naver.com"

                )));
    }

    @Test
    @DisplayName("회원 검색 실패(회원을 찾을 수 없음 - findMemberByLoginId 테스트")
    void 회원을_찾을수_없음_findMemberByLoginId() throws Exception {
        // when then
        assertThatThrownBy(() -> queryMemberRepository.findMemberByLoginId("failedLoginId")
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "LoginId : " + "failedLoginId"

                )));
    }

    @Test
    @DisplayName("회원 검색 실패(회원을 찾을 수 없음 - findMemberByPhone 테스트")
    void 회원을_찾을수_없음_findMemberByPhone() throws Exception {
        // when then
        assertThatThrownBy(() -> queryMemberRepository.findMemberByPhone("010-9999-9999")
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_PHONE_NOT_EXISTS, "LoginId : " + "010-9999-9999"

                )));
    }
    @Test
    @DisplayName("회원 검색 성공 - findById 테스트")
    void 회원검색_findById(){
        // when then
        Member foundMember = queryMemberRepository.findById(member.getId())
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_NOT_EXISTS,
                        "Member Id : " + Id
                ));
        // then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("회원 검색 성공 - findMemberByLoginId 테스트")
    void 회원검색_findMemberByLoginId() throws Exception{
        // when
        Member foundMember = queryMemberRepository.findMemberByLoginId(loginId)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_LOGINID_NOT_EXISTS,
                        "Login Id : " + loginId
                ));

        // then
        assertThat(foundMember.getLoginId()).isEqualTo(loginId);
    }

    @Test
    @DisplayName("회원 검색 성공 - findMemberByEmail 테스트")
    void 회원검색_findMemberByEmail() throws Exception{
        // when
        Member foundMember = queryMemberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_EMAIL_NOT_EXISTS,
                        "Email : " + email
                ));

        // then
        assertThat(foundMember.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("회원 검색 성공 - findMemberByPhone 테스트")
    void 회원검색_findMemberByPhone() throws Exception{
        // when
        Member foundMember = queryMemberRepository.findMemberByPhone(phone)
                .orElseThrow(() -> new ClientException(
                        ErrorCode.MEMBER_PHONE_NOT_EXISTS,
                        "Phone : " + phone
                ));

        // then
        assertThat(foundMember.getPhone()).isEqualTo(phone);
    }

    @Test
    @DisplayName("회원 검증 로직 중 email이 존재할 때")
    void 회원_검증_로직_Email이_존재함() throws Exception{
        // when
        boolean result = queryMemberRepository.existsMemberByEmail(email);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 검증 로직 중 loginId가 존재할 때")
    void 회원_검증_로직_loginId가_존재함() throws Exception{
        // when
        boolean result = queryMemberRepository.existsMemberByLoginId(loginId);

        // then
        assertThat(result).isEqualTo(true);
    }

    @Test
    @DisplayName("회원 검증 로직 중 nickname이 존재할 때")
    void 회원_검증_로직_nickname이_존재함() throws Exception{
        // when
        boolean result = queryMemberRepository.existsMemberByNickname(nickname);

        // then
        assertThat(result).isEqualTo(true);
    }
}
