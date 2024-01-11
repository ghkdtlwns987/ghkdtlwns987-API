package com.ghkdtlwns987.apiserver.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class PasswordEncryptTest {
    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
    }
    private String CORRECT_PASSWORD = "correctPassword@12353";
    @Test
    @DisplayName("null이 들어갔을 때 NullPointerException 발생")
    void nullInput() throws Exception{
        when(passwordEncoder.encode(null)).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            passwordEncoder.encode(null); // null을 인자로 전달하는 경우
        });
    }


    @Test
    @DisplayName("빈 값이 들어가면 Exception 발생")
    void 아무것도_들어가지_않을때()  throws Exception{
        when(passwordEncoder.encode("")).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> {
            passwordEncoder.encode(""); // null을 인자로 전달하는 경우
        });
    }

    @Test
    @DisplayName("암호화를 수행함")
    void 암호화_수행() throws Exception{
        String result = passwordEncoder.encode(CORRECT_PASSWORD);
        assertThat(result).isEqualTo(passwordEncoder.encode(CORRECT_PASSWORD));
    }
}
