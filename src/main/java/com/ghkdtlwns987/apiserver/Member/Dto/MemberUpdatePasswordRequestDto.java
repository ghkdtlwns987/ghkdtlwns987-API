package com.ghkdtlwns987.apiserver.Member.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

/**
 * 회원의 비밀번호를 변경할 떄의 요청을 담을 Dto 클래스 입니다.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberUpdatePasswordRequestDto {
    @NotBlank
    @Size(min = 8, max = 50, message = "비밀번호는 최소 8자 이상으로 입력해야 합니다.")
    private String newPassword;
}
