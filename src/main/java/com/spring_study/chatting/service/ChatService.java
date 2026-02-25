package com.spring_study.chatting.service;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.Message;
import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.dto.request.MessageDto;
import com.spring_study.chatting.dto.response.ChatDto;
import com.spring_study.chatting.dto.response.SliceResponse;
import com.spring_study.chatting.repository.ChatRepository;
import com.spring_study.chatting.repository.MessageRepository;
import com.spring_study.chatting.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public SliceResponse<ChatDto> getChat(Long id,Integer pageNum, Integer pageSize){
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found"));

        Pageable pageable= PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(Sort.Direction.DESC,"created_at")
        );
        Slice<Chat> chats=chatRepository.findAllByUserId(id,pageable);

        List<ChatDto> list=chats.getContent().stream()
                .map(ChatDto::fromEntity)
                .toList();

        return SliceResponse.from(chats,list);
    }
}
