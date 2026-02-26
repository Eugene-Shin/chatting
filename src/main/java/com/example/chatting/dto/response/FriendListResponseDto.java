package com.example.chatting.dto.response;

import com.example.chatting.domain.Friend;

public record FriendListResponseDto (
        Long id,
        Long friendId, //친구 id
        String name, //친구 이름
        String email //친구 이메일
) {
    public static FriendListResponseDto fromEntity(Friend friend) {
        return new FriendListResponseDto(
                friend.getId(),
                friend.getFid().getId(),
                friend.getFid().getName(),
                friend.getFid().getEmail()
        );
    }
}
