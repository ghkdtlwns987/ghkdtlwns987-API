package com.ghkdtlwns987.apiserver.Member.Dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 회원정보를 업데이트 할 때 요청할 데이터를 담는 Dto 입니다.
 */
@Getter
@NoArgsConstructor
public class MemberUpdateNicknameRequestDto {
    //@Size(min = 2, max = 15)
    @Pattern(regexp = "^[가-힣ㄱ-ㅎa-zA-Z0-9._-]{2,15}$",
            message = "숫자, 영어, 한국어와 언더스코어, 공백을 허용하며 최소 2자 이상의 15자 이하의 닉네임만 가능합니다.")
    private String newNickname;

    public MemberUpdateNicknameRequestDto(String newNickname) {
        this.newNickname = newNickname;
    }
}
