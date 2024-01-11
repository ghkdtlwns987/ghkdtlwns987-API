package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원의 암호 변경이 성공했을 때 응답하게될 Dto 입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdatePasswordResponseDto {
    private String loginId;
    private String password;
    private String email;
    private String username;
    private String nickname;
    private String phone;
    private Roles roles;
    private boolean withdrawal;

    public static MemberUpdatePasswordResponseDto fromEntity(Member member){
        return new MemberUpdatePasswordResponseDto(
                member.getLoginId(),
                member.getPassword(),
                member.getEmail(),
                member.getUsername(),
                member.getNickname(),
                member.getPhone(),
                member.getRoles(),
                member.isWithdraw()
        );
    }
}