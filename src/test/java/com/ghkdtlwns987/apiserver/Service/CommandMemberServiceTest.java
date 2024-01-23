package com.ghkdtlwns987.apiserver.Service;

import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberAlreadyExistsException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberLoginIdNotExistsException;
import com.ghkdtlwns987.apiserver.Member.Exception.Class.MemberNicknameAlreadyExistsException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Repository.CommandMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Impl.CommandMemberServiceImpl;

import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
import jakarta.persistence.EntityListeners;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

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
    private QueryMemberService queryMemberService;
    private BCryptPasswordEncoder passwordEncoder;

    Member member;
    MemberCreateRequestDto memberCreateRequestDto;
    @BeforeEach
    void setUp(){
        commandMemberRepository = Mockito.mock(CommandMemberRepository.class);
        queryMemberService = Mockito.mock(QueryMemberService.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

        commandMemberService = new CommandMemberServiceImpl(queryMemberService, commandMemberRepository, passwordEncoder);

        member = Member.builder()
                .loginId(loginId)
                .password(password)
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
    @DisplayName("회원이 이미 존재하므로 등록 실패")
    void 회원이_이미_존재함(){
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);

        // when
        MemberAlreadyExistsException error = assertThrows(MemberAlreadyExistsException.class, () -> commandMemberService.signup(memberCreateRequestDto));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_ALREADY_EXISTS);
        verify(commandMemberRepository, never()).save(any());
    }

    @Test
    @DisplayName("회원 nickname 수정 실패 테스트 - 존재하지 않는 회원")
    void nickname변경시_loginId가_존재하지_않음() throws Exception{
        // given
        doReturn(false).when(queryMemberService).memberExistsByLoginId(loginId);

        // when
        MemberLoginIdNotExistsException error = assertThrows(MemberLoginIdNotExistsException.class, () -> commandMemberService.updatePassword(loginId, newPassword));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_LOGINID_NOT_EXISTS);
    }
    @Test
    @DisplayName("회원 nickname 수정 실패 테스트 - nickname 중복")
    void nickname이_중복되어_변경_실패() throws Exception{
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(true).when(queryMemberService).memberExistsByNickname(newNickanme);

        // when
        MemberNicknameAlreadyExistsException error = assertThrows(MemberNicknameAlreadyExistsException.class, () -> commandMemberService.updateNickname(loginId, newNickanme));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS);
    }

    @Test
    @DisplayName("회원 탈퇴 실패 - loginId가 존재하지 않음")
    void loginId가_존재하지_않아_탈퇴_실패() throws Exception{
        // given
        doReturn(false).when(queryMemberService).memberExistsByLoginId(loginId);

        // when
        MemberLoginIdNotExistsException error = assertThrows(MemberLoginIdNotExistsException.class, () -> commandMemberService.witrawalMember(loginId));

        // then
        assertThat(error.getErrorCode()).isEqualTo(ErrorCode.MEMBER_LOGINID_NOT_EXISTS);
    }

    @Test
    @DisplayName("회원 탈퇴 성공")
    void 회원_탈퇴_성공() throws Exception{
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(member).when(queryMemberService).findMemberByLoginId(loginId);
        // when
        MemberWithdrawalResponseDto actualResponse = commandMemberService.witrawalMember(loginId);

        // then
        assertThat(true).isEqualTo(actualResponse.isWithdrawal());
    }
    @Test
    @DisplayName("회원 password 변경")
    void password_변경_성공() throws Exception{
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(member).when(queryMemberService).findMemberByLoginId(loginId);

        // when
        MemberUpdatePasswordResponseDto response = commandMemberService.updatePassword(loginId, newPassword);

        // then
        assertThat(response.getPassword()).isEqualTo(passwordEncoder.encode(newPassword));
    }
    @Test
    @DisplayName("회원 nickname 수정 테스트")
    void nickname_변경_성공() throws Exception {
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(false).when(queryMemberService).memberExistsByNickname(newNickanme);
        doReturn(member).when(queryMemberService).findMemberByLoginId(loginId);

        // when
        MemberUpdateNicknameResponseDto response = commandMemberService.updateNickname(loginId, newNickanme);

        // then
        assertThat(response.getNickname()).isEqualTo(newNickanme);
    }

    @Test
    @DisplayName("회원 조회 성공")
    void 회원_조회_성공() throws Exception{
        // given
        doReturn(true).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(member).when(queryMemberService).findMemberByLoginId(loginId);
        // when
        MemberGetInformationResponseDto response = commandMemberService.getMemberInfo(loginId);

        // then
        assertThat(response.getLoginId()).isEqualTo(loginId);
    }
    @Test
    @DisplayName("회원 등록 성공")
    void 회원등록_성공() throws Exception {
        // given
        doReturn(false).when(queryMemberService).memberExistsByLoginId(loginId);
        doReturn(false).when(queryMemberService).memberExistsByNickname(nickname);
        doReturn(false).when(queryMemberService).memberExistsByEmail(email);

        memberCreateRequestDto.setPassword(passwordEncoder.encode(password));
        doReturn(member(memberCreateRequestDto)).when(commandMemberRepository).save(any(Member.class));

        // when
        MemberCreateResponseDto result = commandMemberService.signup(memberCreateRequestDto);

        // then
        assertThat(result.getLoginId()).isEqualTo(memberCreateRequestDto.getLoginId());
        assertThat(result.getPassword()).isEqualTo(passwordEncoder.encode(memberCreateRequestDto.getPassword()));
        assertThat(result.getEmail()).isEqualTo(memberCreateRequestDto.getEmail());
        assertThat(result.getUsername()).isEqualTo(memberCreateRequestDto.getUsername());
        assertThat(result.getNickname()).isEqualTo(memberCreateRequestDto.getNickname());
        assertThat(result.getPhone()).isEqualTo(memberCreateRequestDto.getPhone());
        assertThat(result.getRoles()).isEqualTo(member.getRoles());

        verify(commandMemberRepository, times(1)).save(any());
    }

    private Member member(MemberCreateRequestDto memberCreateRequestDto){
        return Member.builder()
                .loginId(memberCreateRequestDto.getLoginId())
                .password(memberCreateRequestDto.getPassword())
                .email(memberCreateRequestDto.getEmail())
                .nickname(memberCreateRequestDto.getNickname())
                .phone(memberCreateRequestDto.getPhone())
                .username(memberCreateRequestDto.getUsername())
                .roles(Collections.singletonList(Roles.USER.getId()))
                .build();
    }
}
