package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberWithdrawalResponseDto {
    private String loginId;
    private String password;
    private String email;
    private String username;
    private String nickname;
    private String phone;
    private List<String> roles;
    private boolean withdrawal;

    public static MemberWithdrawalResponseDto fromEntity(Member member){
        return new MemberWithdrawalResponseDto(
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
