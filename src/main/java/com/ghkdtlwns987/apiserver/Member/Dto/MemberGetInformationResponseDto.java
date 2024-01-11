package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberGetInformationResponseDto {
    private Long Id;
    private String loginId;
    private String password;
    private String email;
    private String nickname;
    private String username;
    private String userId;
    private String phone;
    private Roles roles;
    private boolean withdraw;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static MemberGetInformationResponseDto fromEntity(Member member){
        return new MemberGetInformationResponseDto(
                member.getId(),
                member.getLoginId(),
                member.getPassword(),
                member.getEmail(),
                member.getNickname(),
                member.getUsername(),
                member.getUserId(),
                member.getPhone(),
                member.getRoles(),
                member.isWithdraw(),
                member.getCreateAt(),
                member.getUpdateAt()
        );
    }
}
