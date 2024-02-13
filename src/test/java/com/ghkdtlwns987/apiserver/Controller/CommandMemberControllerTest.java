package com.ghkdtlwns987.apiserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Member.Controller.CommandMemberController;
import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Global.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Repository.QueryMemberRepository;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommandMemberController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CommandMemberControllerTest {
    private final String loginId = "ghkdtlwns987";
    private final String password = "testPassword@12";
    private final String email = "ghkdtlwns987@naver.com";
    private final String nickname = "ghkdtlwns987";
    private final String userName = "황시준";
    private final String phone = "01048482771";

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MockMvc mockMvc;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    QueryMemberRepository queryMemberRepository;
    @MockBean
    CommandMemberService commandMemberService;

    Member member;
    Member withdraw;
    MemberCreateResponseDto memberCreateResponse;
    MemberUpdatePasswordResponseDto memberUpdatePasswordResponse;
    MemberUpdateNicknameResponseDto memberUpdateNicknameResponse;
    MemberWithdrawalResponseDto memberWithdrawalResponseDto;
    MemberGetInformationResponseDto memberGetInformationResponseDto;

    @BeforeEach
    void setUp(){
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        member = Member.builder()
                .Id(1L)
                .username(userName)
                .userId(UUID.randomUUID().toString())
                .password(password)
                .nickname(nickname)
                .loginId(loginId)
                .roles(Collections.singletonList(Roles.USER.getId()))
                .phone(phone)
                .email(email)
                .build();

        String withDrawId = "" + 10L;
        withdraw = Member.builder()
                .Id(10L)
                .username(userName)
                .userId(withDrawId)
                .password("")
                .nickname(withDrawId)
                .loginId(loginId)
                .roles(Collections.singletonList(Roles.USER.getId()))
                .phone(withDrawId)
                .email(email)
                .build();


        memberWithdrawalResponseDto = MemberWithdrawalResponseDto.fromEntity(withdraw);
        memberCreateResponse = MemberCreateResponseDto.fromEntity(member);
        memberUpdatePasswordResponse = MemberUpdatePasswordResponseDto.fromEntity(member);
        memberUpdateNicknameResponse = MemberUpdateNicknameResponseDto.fromEntity(member);
        memberGetInformationResponseDto = MemberGetInformationResponseDto.fromEntity(member);
        queryMemberRepository = Mockito.mock(QueryMemberRepository.class);
    }

    @Test
    void 멤버등록실패_loginId가_7자_이하인_경우() throws Exception{
        final String badLoginId = "loginId";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(badLoginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(phone)
                .build();


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버등록실패 - loginId가 16자 이상인 경우")
    void 멤버등록실패_loginId가_16자_이상인_경우() throws Exception{
        final String badLoginId = "asdfghjjkl123456";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(badLoginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(phone)
                .build();


        //when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);
        when(commandMemberService.signup(any())).thenThrow(new ClientException(ErrorCode.BAD_REQUEST, "유효하지 않는 요청입니다."));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    void 멤버등록실패_password가_8자_이하인경우() throws Exception{
        final String badPassword = "badpw";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(badPassword)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(phone)
                .build();


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[비밀번호는 최소 8자 이상으로 입력해야 합니다.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버등록실패 - nickname이 1글자인 경우")
    void 멤버등록실패_nickname이_1글자인_경우() throws Exception{
        final String badNickname = "B";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(badNickname)
                .username(userName)
                .phone(phone)
                .build();


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }
    @Test
    @DisplayName("멤버등록실패 - nickname이 16자 이상인 경우")
    void 멤버등록실패_nickname이_16글자_이상인_경우() throws Exception{
        final String badNickname = "badNickname12345";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(badNickname)
                .username(userName)
                .phone(phone)
                .build();


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버등록실패 - 잘못된 휴대폰 번호 입력(11글자가 아닌 경우)")
    void 멤버등록실패_phone이_11글자가_아닌_경우() throws Exception{
        final String badPhone = "010-4828-27711";

        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(badPhone)
                .build();


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[휴대폰 번호는 '-'를 제외해 11글자 입력하세요.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버 password 수정 실패 - 유효한 password가 8자 이하인 경우")
    void 멤버수정실패_비밀번호가_8자_이하() throws Exception{
        final String badPassword = "badpw1";

        // given
        MemberUpdatePasswordRequestDto requestDto = new MemberUpdatePasswordRequestDto(badPassword);
//        when(commandMemberService.updatePassword(any(String.class), any(MemberUpdatePasswordRequestDto.class))).thenReturn(null);
        String request = objectMapper.writeValueAsString(requestDto);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/password/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        );

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[비밀번호는 최소 8자 이상으로 입력해야 합니다.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).updatePassword(any(String.class), any(MemberUpdatePasswordRequestDto.class));
    }



    @Test
    @DisplayName("멤버 nickname 수정 실패 - 유효한 nickname이 아님")
    void 멤버수정실패_유효한_nickname이_아님() throws Exception{
        final String badNickname = "badNickname12345";

        // given
        String request = objectMapper.writeValueAsString(badNickname);
        when(commandMemberService.updateNickname(any(String.class), any(String.class))).thenReturn(memberUpdateNicknameResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/nickname/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("[숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.]")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).updateNickname(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("멤버삭제실패 - loginId가 존재하지 않음")
    void 멤버삭제실패_loginId가_존재하지_않음() throws Exception{
        // given
        final String inCorrectLoginId = "incorrect42";

        when(queryMemberRepository.findMemberByLoginId(inCorrectLoginId)).thenThrow(ClientException.class);
        when(commandMemberService.witrawalMember(inCorrectLoginId)).thenThrow(new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "존재하지 않는 LoginId 입니다."));

        // when
        ResultActions perform = mockMvc.perform(delete("/api/v1/member/" + inCorrectLoginId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.message", equalTo("존재하지 않는 LoginId 입니다.")))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    @DisplayName("멤버등록실패_이미_가입된_회원")
    void 멤버등록실패_이미_가입된_회원() throws Exception{
        // given
        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(phone)
                .build();

        when(commandMemberService.signup(any())).thenThrow(new ClientException(ErrorCode.MEMBER_NICKNAME_ALREADY_EXISTS, "이미 가입된 Nickname 입니다."));

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );
        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", equalTo("이미 가입된 Nickname 입니다.")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("멤버 nickname 수정 성공 - 유효한 nickname")
    void 멤버수정성공_유효한_nickname() throws Exception{
        String newNickname = "fffasegas";
        MemberUpdateNicknameRequestDto memberUpdateNicknameRequestDto = new MemberUpdateNicknameRequestDto(newNickname);
        when(queryMemberRepository.existsMemberByLoginId(any(String.class))).thenReturn(true);
        when(queryMemberRepository.existsMemberByNickname(any(String.class))).thenReturn(false);
        when(commandMemberService.updateNickname(any(String.class), any(String.class))).thenReturn(memberUpdateNicknameResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/nickname/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberUpdateNicknameRequestDto))
        );

        // then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M001")))
                .andExpect(jsonPath("$.message", equalTo("회원 NICKNAME 수정 완료")))
                .andExpect(jsonPath("$.data.loginId", equalTo(member.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(member.getPassword())))
                .andExpect(jsonPath("$.data.email", equalTo(member.getEmail())))
                .andExpect(jsonPath("$.data.username", equalTo(member.getUsername())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(member.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo(member.getRoles())))
                .andExpect(jsonPath("$.data.withdrawal", equalTo(member.isWithdraw())))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).updateNickname(any(String.class), any(String.class));
    }

    @Test
    @DisplayName("멤버 password 수정 성공 - 유효한 password")
    void 멤버수정성공_유효한_password() throws Exception{
        String correctPassword = "newPassword124512";
        String changedPassword = passwordEncoder.encode(correctPassword);
        MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto = new MemberUpdatePasswordRequestDto();
        memberUpdatePasswordRequestDto.setNewPassword(correctPassword);

        when(queryMemberRepository.existsMemberByLoginId(any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(String.class))).thenReturn(changedPassword);
        when(commandMemberService.updatePassword(any(String.class), any(MemberUpdatePasswordRequestDto.class))).thenReturn(memberUpdatePasswordResponse);


        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/password/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberUpdatePasswordRequestDto))
        );

        // then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M001")))
                .andExpect(jsonPath("$.message", equalTo("회원 PASSWORD 수정 완료")))
                .andExpect(jsonPath("$.data.loginId", equalTo(member.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(member.getPassword())))
                .andExpect(jsonPath("$.data.username", equalTo(member.getUsername())))
                .andExpect(jsonPath("$.data.email", equalTo(member.getEmail())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(member.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo(member.getRoles())))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).updatePassword(any(String.class), any(MemberUpdatePasswordRequestDto.class));
    }


    @Test
    @DisplayName("멤버등록성공")
    void 멤버등록성공() throws Exception{
        // given
        MemberCreateRequestDto request = MemberCreateRequestDto.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(userName)
                .phone(phone)
                .build();

        Member member = request.toEntity();
        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M001")))
                .andExpect(jsonPath("$.message", equalTo("회원가입 되었습니다.")))
                .andExpect(jsonPath("$.data.loginId", equalTo(member.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(member.getPassword())))
                .andExpect(jsonPath("$.data.email", equalTo(member.getEmail())))
                .andExpect(jsonPath("$.data.username", equalTo(member.getUsername())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(member.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo(member.getRoles())))
                .andExpect(jsonPath("$.data.withdrawal", equalTo(false))) // 추가된 부분
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).signup(any());
    }

    @Test
    @DisplayName("멤버삭제성공")
    void 멤버삭제성공() throws Exception{
        // given
        when(queryMemberRepository.existsMemberByLoginId(any())).thenReturn(true);
        when(queryMemberRepository.isWithdraw()).thenReturn(memberWithdrawalResponseDto.isWithdrawal());
        when(commandMemberService.witrawalMember(any())).thenReturn(memberWithdrawalResponseDto);

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v1/member/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M007")))
                .andExpect(jsonPath("$.message", equalTo("회원탈퇴 완료")))
                .andExpect(jsonPath("$.data.loginId", equalTo(memberWithdrawalResponseDto.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(memberWithdrawalResponseDto.getPassword())))
                .andExpect(jsonPath("$.data.email", equalTo(memberWithdrawalResponseDto.getEmail())))
                .andExpect(jsonPath("$.data.username", equalTo(memberWithdrawalResponseDto.getUsername())))
                .andExpect(jsonPath("$.data.nickname", equalTo(memberWithdrawalResponseDto.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(memberWithdrawalResponseDto.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo(member.getRoles())))
                .andExpect(jsonPath("$.data.withdrawal", equalTo(memberWithdrawalResponseDto.isWithdrawal())))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).witrawalMember(any());
    }

    @Test
    @DisplayName("멤버조회성공")
    void 멤버조회성공() throws Exception{
        // given
        when(queryMemberRepository.existsMemberByLoginId(loginId)).thenReturn(true);
        when(commandMemberService.getMemberInfo(loginId)).thenReturn(memberGetInformationResponseDto);

        //when
        ResultActions perform = mockMvc.perform(get("/api/v1/member/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M001")))
                .andExpect(jsonPath("$.message", equalTo("회원 조회 완료")))
                .andExpect(jsonPath("$.data.loginId", equalTo(memberGetInformationResponseDto.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(memberGetInformationResponseDto.getPassword())))
                .andExpect(jsonPath("$.data.email", equalTo(memberGetInformationResponseDto.getEmail())))
                .andExpect(jsonPath("$.data.nickname", equalTo(memberGetInformationResponseDto.getNickname())))
                .andExpect(jsonPath("$.data.username", equalTo(memberGetInformationResponseDto.getUsername())))
                .andExpect(jsonPath("$.data.userId", equalTo(memberGetInformationResponseDto.getUserId())))
                .andExpect(jsonPath("$.data.phone", equalTo(memberGetInformationResponseDto.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo(member.getRoles())))
                .andExpect(jsonPath("$.data.withdraw", equalTo(memberGetInformationResponseDto.isWithdraw())))
                .andExpect(jsonPath("$.data.id", equalTo(1)))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
        verify(commandMemberService, times(1)).getMemberInfo(any());
    }
}

