package com.spring_study.chatting.dto.request;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.Message;
import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.domain.type.MessageStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MessageDto(
        Long id,
        Long sender,
        Long receiver,
        LocalDateTime created_at,
        String data,
        MessageStatus messageStatus
) {
    public Message toEntity(Chat chat){
        return Message.builder()
                .chat_id(chat)
                .created_at(created_at)
                .data(data)
                .status(messageStatus)
                .build();
    }
}
