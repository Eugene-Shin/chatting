package com.spring_study.chatting.dto.response;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.Message;
import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.domain.type.MessageStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MessageDto(
        Long id,
        User sender,
        Chat chat,
        LocalDateTime created_at,
        MessageStatus messageStatus,
        String data,
        Boolean isDelete
) {
    public static MessageDto fromEntity(Message message){
        return new MessageDto(
                message.getId(),
                message.getSender(),
                message.getChat(),
                message.getCreated_at(),
                message.getStatus(),
                message.getData(),
                message.getIsDelete()
        );
    }
}
