package com.spring_study.chatting.domain;

import com.spring_study.chatting.domain.type.Provider;
import com.spring_study.chatting.domain.type.UserRole;
import com.spring_study.chatting.domain.type.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"provider", "social_id"})
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column
    private Sex sex;

    @Column
    private LocalDate birthday;

    @Lob
    @Column
    private String introduce;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(name = "social_id")
    private String socialId;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        if(uuid == null) {
            uuid = UUID.randomUUID();
        }

        createdAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, String password, String name, String phone, Sex sex, LocalDate birthday, String introduce, String picture) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.introduce = introduce;
        this.picture = picture;

        this.role = UserRole.USER;
        this.provider = Provider.LOCAL;

        validate();
    }

    //생성자 안에서 사용하기
    private void validate() {
        if(provider == Provider.LOCAL && password == null) {
            throw new IllegalStateException("LOCAL 계정은 비밀번호가 필요합니다.");
        }
    }
}
