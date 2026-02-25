package com.spring_study.chatting.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.domain.type.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record LocalSignupRequestDto(

        @Email
        @NotBlank
        String email,

        @NotBlank
        String password,

        @NotBlank
        String name,

        String phone,

        Sex sex,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate birthday,

        String introduce,

        String picture
) {
}
