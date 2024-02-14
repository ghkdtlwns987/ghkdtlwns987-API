package com.ghkdtlwns987.apiserver.Member.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "loginId", nullable = false, unique = true, length = 20)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickname;

    @Column(name = "username", nullable = false, length = 8)
    private String username;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "phone", unique = true, nullable = false, length = 11)
    private String phone;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> roles = new ArrayList<>();

    @Column(name = "withdraw", nullable = false)
    private boolean withdraw;

    @Column(name = "createAt", nullable = false, updatable = false)
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime createAt;

    @Column(name = "modifiedAt")
    @UpdateTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateAt;


    @Builder
    public Member (Long Id, String loginId, String password, String email, String nickname, String username, String userId, String phone, List<String> roles){
        this.Id = Id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.username = username;
        this.userId = userId;
        this.phone = phone;
        this.roles = roles;
        this.withdraw = false;
    }

    public void updateMemberNickname(String updateNickname){
        this.nickname = updateNickname;
    }

    public void updateMemberPassword(String newPassword){
        this.password = newPassword;

    }

    public void withdrawMember() {
        String uniqueField = "" + this.Id;
        this.password = "";
        this.nickname = uniqueField;
        this.userId = uniqueField;
        this.phone = uniqueField;
        this.updateAt = LocalDateTime.now();
        this.withdraw = true;
    }
}
