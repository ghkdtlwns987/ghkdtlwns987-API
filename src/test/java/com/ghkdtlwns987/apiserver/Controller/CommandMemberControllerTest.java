package com.ghkdtlwns987.apiserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghkdtlwns987.apiserver.Member.Controller.CommandMemberController;
import com.ghkdtlwns987.apiserver.Member.Dto.*;
import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import com.ghkdtlwns987.apiserver.Member.Exception.ClientException;
import com.ghkdtlwns987.apiserver.Member.Exception.ErrorCode;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.CommandMemberService;
import com.ghkdtlwns987.apiserver.Member.Service.Inter.QueryMemberService;
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

import java.time.LocalDateTime;
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
    QueryMemberService queryMemberService;
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
                .roles(Roles.USER)
                .phone(phone)
                .email(email)
                .roles(Roles.USER)
                .build();

        String withDrawId = "" + 10L;
        withdraw = Member.builder()
                .Id(10L)
                .username(userName)
                .userId(withDrawId)
                .password("")
                .nickname(withDrawId)
                .loginId(loginId)
                .roles(Roles.USER)
                .phone(withDrawId)
                .email(email)
                .roles(Roles.USER)
                .build();


        memberWithdrawalResponseDto = MemberWithdrawalResponseDto.fromEntity(withdraw);
        memberCreateResponse = MemberCreateResponseDto.fromEntity(member);
        memberUpdatePasswordResponse = MemberUpdatePasswordResponseDto.fromEntity(member);
        memberUpdateNicknameResponse = MemberUpdateNicknameResponseDto.fromEntity(member);
        memberGetInformationResponseDto = MemberGetInformationResponseDto.fromEntity(member);
        queryMemberService = Mockito.mock(QueryMemberService.class);
    }

    @Test
    @DisplayName("멤버등록실패 - loginId가 8자 이하인 경우")
    void 멤버등록실패_loginId가_8자_이하인_경우() throws Exception{
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
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버등록실패 - loginId가 16자 이인 경우")
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


        when(commandMemberService.signup(any())).thenReturn(memberCreateResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버등록실패 - loginId가 16자 이인 경우")
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
                .andExpect(jsonPath("$.data", equalTo(null)))
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
                .andExpect(jsonPath("$.data", equalTo(null)))
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
                .andExpect(jsonPath("$.data", equalTo(null)))
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
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).signup(any());
    }

    @Test
    @DisplayName("멤버 password 수정 실패 - 유효한 password가 8자 이하인 경우")
    void 멤버수정실패_비밀번호가_8자_이하() throws Exception{
        final String badPassword = "badpw12";
        // given
        String request = objectMapper.writeValueAsString(badPassword);
        when(commandMemberService.updatePassword(loginId, badPassword)).thenReturn(memberUpdatePasswordResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/password/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).updatePassword(loginId, badPassword);
    }

    @Test
    @DisplayName("멤버 nickname 수정 실패 - 유효한 nickname이 아님")
    void 멤버수정실패_유효한_nickname이_아님() throws Exception{
        final String badNickname = "badNickname12345";

        // given
        String request = objectMapper.writeValueAsString(badNickname);
        when(commandMemberService.updateNickname(loginId, badNickname)).thenReturn(memberUpdateNicknameResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/nickname/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data", equalTo(null)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(commandMemberService, times(0)).updateNickname(loginId, badNickname);
    }

    @Test
    @DisplayName("멤버삭제실패 - loginId가 존재하지 않음")
    void 멤버삭제실패_loginId가_존재하지_않음() throws Exception{
        // given
        final String inCorrectLoginId = "incorrect42";

        when(queryMemberService.memberExistsByLoginId(inCorrectLoginId)).thenReturn(false);
        when(commandMemberService.witrawalMember(inCorrectLoginId)).thenThrow(new ClientException(ErrorCode.MEMBER_LOGINID_NOT_EXISTS, "MEMBER LOGIN_ID NOT EXISTS"));

        // when
        ResultActions perform = mockMvc.perform(delete("/api/v1/member/" + inCorrectLoginId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andDo(print()).andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.data", equalTo(null)))
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }
    @Test
    @DisplayName("멤버 nickname 수정 성공 - 유효한 nickname")
    void 멤버수정성공_유효한_nickname() throws Exception{
        String newNickname = "fffasegas";
        MemberUpdateNicknameRequestDto memberUpdateNicknameRequestDto = new MemberUpdateNicknameRequestDto(newNickname);
        when(queryMemberService.memberExistsByLoginId(loginId)).thenReturn(true);
        when(queryMemberService.memberExistsByNickname(nickname)).thenReturn(false);
        when(commandMemberService.updateNickname(loginId, newNickname)).thenReturn(memberUpdateNicknameResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/nickname/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberUpdateNicknameRequestDto))
        );

        // then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId", equalTo(member.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(member.getPassword())))
                .andExpect(jsonPath("$.data.username", equalTo(member.getUsername())))
                .andExpect(jsonPath("$.data.email", equalTo(member.getEmail())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(member.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo("USER")))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).updateNickname(loginId, newNickname);
    }

    @Test
    @DisplayName("멤버 password 수정 성공 - 유효한 password")
    void 멤버수정성공_유효한_password() throws Exception{
        String correctPassword = "newPassword124512";
        String changedPassword = passwordEncoder.encode(correctPassword);
        MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto = new MemberUpdatePasswordRequestDto(loginId, correctPassword);

        when(queryMemberService.memberExistsByLoginId(loginId)).thenReturn(true);
        when(passwordEncoder.encode(correctPassword)).thenReturn(changedPassword);
        when(commandMemberService.updatePassword(loginId, correctPassword)).thenReturn(memberUpdatePasswordResponse);

        // when
        ResultActions perform = mockMvc.perform(put("/api/v1/member/password/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberUpdatePasswordRequestDto))
        );

        // then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.loginId", equalTo(member.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(member.getPassword())))
                .andExpect(jsonPath("$.data.username", equalTo(member.getUsername())))
                .andExpect(jsonPath("$.data.email", equalTo(member.getEmail())))
                .andExpect(jsonPath("$.data.nickname", equalTo(member.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(member.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo("USER")))
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).updatePassword(loginId, correctPassword);
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
                .andExpect(jsonPath("$.data.roles", equalTo("USER")))
                .andExpect(jsonPath("$.data.withdrawal", equalTo(false))) // 추가된 부분
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
        ;


        verify(commandMemberService, times(1)).signup(any());
    }

    @Test
    @DisplayName("멤버삭제성공")
    void 멤버삭제성공() throws Exception{
        // given
        when(queryMemberService.memberExistsByLoginId(loginId)).thenReturn(true);
        when(commandMemberService.witrawalMember(loginId)).thenReturn(memberWithdrawalResponseDto);

        //when
        ResultActions perform = mockMvc.perform(delete("/api/v1/member/" + loginId)
                .contentType(MediaType.APPLICATION_JSON)
        );

        //then
        perform.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("M001")))
                .andExpect(jsonPath("$.message", equalTo("회원가입 되었습니다.")))
                .andExpect(jsonPath("$.data.loginId", equalTo(memberWithdrawalResponseDto.getLoginId())))
                .andExpect(jsonPath("$.data.password", equalTo(memberWithdrawalResponseDto.getPassword())))
                .andExpect(jsonPath("$.data.email", equalTo(memberWithdrawalResponseDto.getEmail())))
                .andExpect(jsonPath("$.data.username", equalTo(memberWithdrawalResponseDto.getUsername())))
                .andExpect(jsonPath("$.data.nickname", equalTo(memberWithdrawalResponseDto.getNickname())))
                .andExpect(jsonPath("$.data.phone", equalTo(memberWithdrawalResponseDto.getPhone())))
                .andExpect(jsonPath("$.data.roles", equalTo("USER")))
                .andExpect(jsonPath("$.data.withdrawal", equalTo(memberWithdrawalResponseDto.isWithdrawal()))) // 추가된 부분
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));

        verify(commandMemberService, times(1)).witrawalMember(any());
    }

    @Test
    @DisplayName("멤버조회성공")
    void 멤버조회성공() throws Exception{
        // given
        when(queryMemberService.memberExistsByLoginId(loginId)).thenReturn(true);
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
                .andExpect(jsonPath("$.data.roles", equalTo("USER")))
                .andExpect(jsonPath("$.data.withdraw", equalTo(memberGetInformationResponseDto.isWithdraw())))
                .andExpect(jsonPath("$.data.id", equalTo(1)))

                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));


        verify(commandMemberService, times(1)).getMemberInfo(any());
    }
}

