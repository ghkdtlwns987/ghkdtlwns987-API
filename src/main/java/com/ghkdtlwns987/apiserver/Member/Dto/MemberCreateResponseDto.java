package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberCreateResponseDto {
    private String loginId;
    private String password;
    private String email;
    private String username;
    private String nickname;
    private String phone;
    private List<String> roles;
    private boolean withdrawal;

    public static MemberCreateResponseDto fromEntity(Member member){
        return new MemberCreateResponseDto(
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
