package com.example.chatting.domain;


import com.example.chatting.domain.enums.FriendEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "friend")
@NoArgsConstructor
@Getter
@DynamicUpdate
@Builder
@AllArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private User uid;

    @ManyToOne
    @JoinColumn(name = "fid", nullable = false)
    private User fid;

    @Column(name = "created_at")
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private FriendEnum status;

    public Friend(User uid, User fid, LocalDate createdAt, FriendEnum status) {
        this.uid = uid;
        this.fid = fid;
        this.createdAt = createdAt;
        this.status = status;
    }

    public void updateStatus(FriendEnum status) {
        this.status = status;
    }
}

