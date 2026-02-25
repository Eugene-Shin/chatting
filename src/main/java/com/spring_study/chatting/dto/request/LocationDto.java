package com.spring_study.chatting.dto.request;

import java.time.Instant;

public record LocationDto(
        Double latitude,
        Double longitude,
        Instant requested_at
) {
}
