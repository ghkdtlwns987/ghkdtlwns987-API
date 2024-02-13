package com.ghkdtlwns987.apiserver.Member.Dto;

import com.ghkdtlwns987.apiserver.Member.Entity.Member;
import com.ghkdtlwns987.apiserver.Member.Entity.Roles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Collections;
import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateRequestDto {
    @NotBlank
    //@Size(min = 8, max = 15, message = "영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "영문 또는 숫자로 8자 이상 15자 이하만 가능 합니다")
    private String loginId;

    @NotBlank
    @Size(min = 8, max = 50, message = "비밀번호는 최소 8자 이상으로 입력해야 합니다.")
    private String password;

    @Email
    @Size
    @NotBlank
    private String email;

    //@Size(min = 2, max = 15)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$",
            message = "숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.")
    private String nickname;

    @NotBlank
    @Size(min = 2, max = 50)
    private String username;

    @NotBlank
    @Size(min = 11, max = 11, message = "휴대폰 번호는 '-'를 제외해 11글자 입력하세요.")
    private String phone;

    public Member toEntity(){
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .email(email)
                .nickname(nickname)
                .username(username)
                .phone(phone)
                .userId(UUID.randomUUID().toString())
                .roles(Collections.singletonList(Roles.USER.getId()))
                .build();
    }
}
