package com.example.chatting.dto.response;

import com.example.chatting.domain.FriendRequest;

import java.time.LocalDate;

public record FriendRequestResponseDto (
    Long id,
    String name, //요청 보낸 사람 이름
    String email, //요청 보낸 사람 email
    LocalDate createdAt //요청 보낸 날짜
){
    public static FriendRequestResponseDto fromEntity(FriendRequest friendRequest) {
        return new FriendRequestResponseDto(
                friendRequest.getId(),
                friendRequest.getRequester().getName(),
                friendRequest.getRequester().getEmail(),
                friendRequest.getCreatedAt()
        );
    }
}
