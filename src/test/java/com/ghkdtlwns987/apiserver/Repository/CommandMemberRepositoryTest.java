package com.ghkdtlwns987.apiserver.Repository;


import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Member.Persistent.JpaMemberRepository;

import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Impl.QueryMemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@DataJpaTest
public class CommandMemberRepositoryTest {
    private final String loginId = "ghkdtlwns987";
    private final String password = "testPassword";
    private final String email = "ghkdtlwns987@naver.com";
    private final String nickname = "testNickname";
    private final String userName = "황시준";
    private final String userId = "testUserId";
    private final String phone = "01048482771";
    private final Roles roles = Roles.USER;

    @Autowired
    private JpaMemberRepository commandMemberRepository;

    @Test
    @DisplayName("성공 테스트(save) - 멤버 전체 저장")
    void 성공_테스트_회원_등록_성공_save_메소드_호출() {
        Member member = Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .userId(userId)
                .phone(phone)
                .roles(roles)
                .build();

        Member savedMember = commandMemberRepository.save(member);

        assertThat(savedMember.getId()).isNotNull();
        assertThat(savedMember.getLoginId()).isEqualTo(loginId);
        assertThat(savedMember.getPassword()).isEqualTo(password);
        assertThat(savedMember.getEmail()).isEqualTo(email);
        assertThat(savedMember.getNickname()).isEqualTo(nickname);
        assertThat(savedMember.getUsername()).isEqualTo(userName);
        assertThat(savedMember.getUserId()).isEqualTo(userId);
        assertThat(savedMember.getPhone()).isEqualTo(phone);
        assertThat(savedMember.getRoles()).isEqualTo(roles);
    }
}
