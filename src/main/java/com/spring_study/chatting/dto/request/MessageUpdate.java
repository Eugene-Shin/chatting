package com.spring_study.chatting.dto.request;

import com.spring_study.chatting.domain.type.MessageStatus;

public record MessageUpdate(
        String data,
        MessageStatus messageStatus,
        Long sender
) {
}
