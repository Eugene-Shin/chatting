package com.spring_study.chatting.service;

import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.dto.request.LocalSignupRequestDto;
import com.spring_study.chatting.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long createLocalUser(LocalSignupRequestDto localSignupRequestDto) {
        if (userRepository.existsByEmail(localSignupRequestDto.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
                .email(localSignupRequestDto.email())
                .password(passwordEncoder.encode(localSignupRequestDto.password()))
                .name(localSignupRequestDto.name())
                .phone(localSignupRequestDto.phone())
                .sex(localSignupRequestDto.sex())
                .birthday(localSignupRequestDto.birthday())
                .introduce(localSignupRequestDto.introduce())
                .picture(localSignupRequestDto.picture()).build();

        userRepository.save(newUser);

        return newUser.getId();
    }
}
