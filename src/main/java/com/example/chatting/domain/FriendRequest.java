package com.example.chatting.domain;

import com.example.chatting.domain.enums.FriendEnum;
import com.example.chatting.domain.enums.FriendRequestEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "friend_request")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester", nullable = false)
    private User requester; //요청 보낸 사람

    @ManyToOne
    @JoinColumn(name = "respondent", nullable = false)
    private User respondent; //요청 받는 사람

    @Enumerated(EnumType.STRING)
    private FriendRequestEnum status;

    @Column(name = "created_at")
    private LocalDate createdAt;

    public FriendRequest(LocalDate createdAt, FriendRequestEnum status, User respondent, User requester) {
        this.createdAt = createdAt;
        this.status = status;
        this.respondent = respondent;
        this.requester = requester;
    }

    public void updateStatus(FriendRequestEnum status) {
        this.status = status;
    }
}
