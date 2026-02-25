package com.spring_study.chatting.dto.request;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.User;

import java.time.LocalDate;

public record ChatDto(
        Long id,
        LocalDate created_at,
        Long uid1,
        Long uid2
) {
    public Chat toEntity(User u1,User u2){
        return Chat.builder()
                .created_at(created_at)
                .uid1(u1)
                .uid2(u2)
                .build();
    }
}
