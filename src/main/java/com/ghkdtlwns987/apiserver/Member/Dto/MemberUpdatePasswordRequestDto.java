package com.ghkdtlwns987.apiserver.Member.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원의 비밀번호를 변경할 떄의 요청을 담을 Dto 클래스 입니다.
 */
@Getter
@NoArgsConstructor
public class MemberUpdatePasswordRequestDto {
    private String loginId;
    @NotBlank
    @Size(min = 8, max = 50, message = "비밀번호는 최소 8자 이상으로 입력해야 합니다.")
    private String newPassword;
    public MemberUpdatePasswordRequestDto(String loginId, String newPassword) {
        this.loginId = loginId;
        this.newPassword = newPassword;
    }
}
