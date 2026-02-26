package com.example.chatting.dto.request;

import com.example.chatting.domain.FriendRequest;
import com.example.chatting.domain.User;
import com.example.chatting.domain.enums.FriendRequestEnum;

import java.time.LocalDate;
import java.util.UUID;

public record FriendRequestDto (
        //누구한테 친구를 요청할지 입력
        UUID respondent
){
    public FriendRequest toEntity(User requester, User respondent) {
        return FriendRequest.builder()
                .requester(requester)
                .respondent(respondent)
                .createdAt(LocalDate.now())
                .status(FriendRequestEnum.PENDING)
                .build();
    }
}