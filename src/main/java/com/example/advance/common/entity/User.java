package com.example.advance.common.entity;

import com.example.advance.common.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum roleEnum;

    public User(String username, String password, String email, UserRoleEnum roleEnum) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleEnum = roleEnum;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
