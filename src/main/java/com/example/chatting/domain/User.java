package com.example.chatting.domain;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;


//임시
@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@DynamicUpdate
@Builder
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="email")
    @Email
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "uuid")
    private UUID uuid;

    public User(UUID uuid, String name, String email) {
        this.uuid = uuid;
        this.name = name;
        this.email = email;
    }
}
