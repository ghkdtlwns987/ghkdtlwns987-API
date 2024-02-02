package com.ghkdtlwns987.apiserver.Member.Service.Inter;

import com.ghkdtlwns987.apiserver.Member.Dto.*;

public interface CommandMemberService {
    /**
     * 회원가입 메서드입니다.
     * @param memberCreateRequestDto
     * @return Member
     */
    MemberCreateResponseDto signup(MemberCreateRequestDto memberCreateRequestDto);

    /**
     * Password 를 업데이트 합니다.
     * @param newPassword
     * @return MemberUpdatePasswordResponseDto
     */
    MemberUpdatePasswordResponseDto updatePassword(String loginId, MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto);

    /**
     * nickname 을 업데이트 합니다.
     * @param newNickname
     * @return MemberUpdateNicknameResponseDto
     */
    MemberUpdateNicknameResponseDto updateNickname(String loginId, String newNickname);

    /**
     * 회원탈퇴를 수행합니다.
     * @param loginId
     * @return MemberWithdrawalResponseDto
     */
    MemberWithdrawalResponseDto witrawalMember(String loginId);

    /**
     * 회원을 조회합니다.
     * @param loginId
     * @return MemberGetInformationResponseDto
     * @throws Exception
     */
    MemberGetInformationResponseDto getMemberInfo(String loginId);
}
