package com.spring_study.chatting.dto.response;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.User;

import java.time.LocalDate;

public record ChatDto(
    Long id,
    LocalDate created_at,
    User uid1,
    User uid2
) {
    public static ChatDto fromEntity(Chat chat){
        return new ChatDto(
                chat.getId(),
                chat.getCreated_at(),
                chat.getUid1(),
                chat.getUid2()
        );
    }
}
