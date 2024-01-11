package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Nickname 변경 후 응답할 데이터를 담은 Dto입니다.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateNicknameResponseDto {
    private String loginId;
    private String password;
    private String email;
    private String username;
    private String nickname;
    private String phone;
    private Roles roles;
    private boolean withdrawal;

    public static MemberUpdateNicknameResponseDto fromEntity(Member member){
        return new MemberUpdateNicknameResponseDto(
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
