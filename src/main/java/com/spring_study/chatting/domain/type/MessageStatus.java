package com.spring_study.chatting.domain.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MessageStatus {
    PICTURE("PICTURE"),CHAT("CHAT");

    private final String messageStatus;

    @Override
    public String toString(){
        return messageStatus;
    }
}
