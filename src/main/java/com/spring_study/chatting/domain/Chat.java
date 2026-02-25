package com.spring_study.chatting.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name="chat")
@Getter
@NoArgsConstructor
@DynamicUpdate
public class Chat {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_at")
    @NotNull
    private LocalDate created_at;

    @ManyToOne
    @JoinColumn(name="uid1")
    @NotNull
    private User uid1;

    @ManyToOne
    @JoinColumn(name="uid2")
    @NotNull
    private User uid2;

    @Builder
    public Chat(Long id, LocalDate created_at, User uid1, User uid2) {
        this.id = id;
        this.created_at = created_at;
        this.uid1 = uid1;
        this.uid2 = uid2;
    }
}
