package com.ghkdtlwns987.apiserver.Member.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Roles {
    USER("USER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    ;

    private String role;
}
