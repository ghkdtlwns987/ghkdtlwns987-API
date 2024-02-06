package com.ghkdtlwns987.apiserver.Service;

import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Repository.QueryMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Impl.CommandMemberServiceImpl;

import com.ghkdtlwns987.apiserver.Member.Service.Impl.QueryMemberServiceImpl;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@EntityListeners(AuditingEntityListener.class)
@ExtendWith(MockitoExtension.class)
public class CommandMemberServiceTest {
    private final String loginId = "ghkdtlwns987";
    private final String password = "testPassword";
    private final String email = "ghkdtlwns987@naver.com";
    private final String nickname = "testNickname";
    private final String userName = "황시준";
    private final String userId = "testUserId";
    private final String phone = "01048482771";
    private final String newPassword = "newPassword@1234";
    private final String newNickanme = "newNickname432";
    private CommandMemberRepository commandMemberRepository;
    private CommandMemberServiceImpl commandMemberService;

    private QueryMemberRepository queryMemberRepository;
    private QueryMemberServiceImpl queryMemberService;
    private BCryptPasswordEncoder passwordEncoder;

    Member member;
    Member withdrawd;
    MemberCreateRequestDto memberCreateRequestDto;
    @BeforeEach
    void setUp(){
        commandMemberRepository = Mockito.mock(CommandMemberRepository.class);
        queryMemberRepository = Mockito.mock(QueryMemberRepository.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        queryMemberService = new QueryMemberServiceImpl(queryMemberRepository);
        commandMemberService = new CommandMemberServiceImpl(queryMemberService, commandMemberRepository);

        member = Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .userId(userId)
                .phone(phone)
                .roles(Collections.singletonList(Roles.USER.getId()))
                .build();


        memberCreateRequestDto = MemberCreateRequestDto
                .builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .phone(phone)
                .username(userName)
                .build();

    }

    @Test
    void 회원이_이미_존재함(){

        // given
        doReturn(true).when(queryMemberRepository).existsMemberByLoginId(loginId);

        // when
        assertThrows(ClientException.class, () -> commandMemberService.signup(memberCreateRequestDto));

        // then
        verify(commandMemberRepository, never()).save(any());
    }

    @Test
    void nickname_변경시_loginId가_존재하지_않음() {
        // when
        ClientException error = assertThrows(ClientException.class, () -> commandMemberService.updateNickname(loginId, newPassword));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_LOGINID_NOT_EXISTS);
    }
    @Test
    void nickname_이_중복되어_변경_실패() {
        // given
        doReturn(false).when(queryMemberRepository).existsMemberByLoginId(loginId);

        // when
        ClientException error = assertThrows(ClientException.class, () -> commandMemberService.updateNickname(loginId, newNickanme));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }

    @Test
    void loginId가_존재하지_않아_탈퇴_실패() {
        // when
        ClientException error = assertThrows(ClientException.class, () -> commandMemberService.witrawalMember(loginId));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_LOGINID_NOT_EXISTS);

    }

    @Test
    void 회원_탈퇴_성공() {
        // given
        String uniqueField = "" + 100L;
        withdrawd = Member.builder()
                .password("")
                .nickname(uniqueField)
                .username(uniqueField)
                .userId(uniqueField)
                .phone(uniqueField)
                .roles(Collections.singletonList(Roles.USER.getId()))
                .build();

        withdrawd.withdrawMember();

        MemberWithdrawalResponseDto response = MemberWithdrawalResponseDto.fromEntity(withdrawd);

        doReturn(Optional.of(member)).when(queryMemberRepository).findMemberByLoginId(loginId);

        commandMemberService.witrawalMember(loginId);

        // then
        assertThat(true).isEqualTo(response.isWithdrawal());
    }
    @Test
    void password_변경_성공() {
        // given
        MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto = new MemberUpdatePasswordRequestDto();
        memberUpdatePasswordRequestDto.setNewPassword(passwordEncoder.encode(newPassword));

        doReturn(Optional.of(member)).when(queryMemberRepository).findMemberByLoginId(loginId);

        // when
        MemberUpdatePasswordResponseDto response = commandMemberService.updatePassword(loginId, memberUpdatePasswordRequestDto);

        // then
        assertThat(response.getPassword()).isEqualTo(passwordEncoder.encode(newPassword));
    }
    @Test
    void nickname_변경_성공() {
        // given
        doReturn(Optional.of(member)).when(queryMemberRepository).findMemberByLoginId(loginId);

        // when
        MemberUpdateNicknameResponseDto response = commandMemberService.updateNickname(loginId, newNickanme);

        // then
        assertThat(response.getNickname()).isEqualTo(newNickanme);
    }

    @Test
    void 회원_조회_성공() {
        // given
        doReturn(Optional.of(member)).when(queryMemberRepository).findMemberByLoginId(loginId);
        // when
        MemberGetInformationResponseDto response = commandMemberService.getMemberInfo(loginId);

        // then
        assertThat(response.getLoginId()).isEqualTo(loginId);
    }
    @Test
    void 회원등록_성공()  {
        // given
        MemberCreateResponseDto result = MemberCreateResponseDto.fromEntity(member);
        doReturn(member).when(commandMemberRepository).save(any(Member.class));

        // when
        commandMemberService.signup(memberCreateRequestDto);
        // then
        assertThat(result.getLoginId()).isEqualTo(memberCreateRequestDto.getLoginId());
        assertThat(result.getPassword()).isEqualTo(memberCreateRequestDto.getPassword());
        assertThat(result.getEmail()).isEqualTo(memberCreateRequestDto.getEmail());
        assertThat(result.getUsername()).isEqualTo(memberCreateRequestDto.getUsername());
        assertThat(result.getNickname()).isEqualTo(memberCreateRequestDto.getNickname());
        assertThat(result.getPhone()).isEqualTo(memberCreateRequestDto.getPhone());
        assertThat(result.getRoles()).isEqualTo(member.getRoles());

        verify(commandMemberRepository, times(1)).save(any());
    }
}
