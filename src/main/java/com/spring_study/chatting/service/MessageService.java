package com.spring_study.chatting.service;

import com.spring_study.chatting.domain.Chat;
import com.spring_study.chatting.domain.Message;
import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.dto.request.LocationDto;
import com.spring_study.chatting.dto.request.MessageDto;
import com.spring_study.chatting.dto.request.MessageSearch;
import com.spring_study.chatting.dto.request.MessageUpdate;
import com.spring_study.chatting.dto.response.SliceResponse;
import com.spring_study.chatting.repository.ChatRepository;
import com.spring_study.chatting.repository.MessageRepository;
import com.spring_study.chatting.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public Boolean sendMessage(MessageDto messageDto){
        User sender=userRepository.findById(messageDto.sender()).orElseThrow(()->new IllegalArgumentException("Sender not found"));
        User receiver=userRepository.findById(messageDto.receiver()).orElseThrow(()->new IllegalArgumentException("Receiver not found"));

        Chat chat=chatRepository.findChatByUsers(sender.getId(),receiver.getId())
                .orElseGet(()->
                        chatRepository.save(
                                Chat.builder()
                                        .uid1(sender)
                                        .uid2(receiver)
                                        .created_at(LocalDate.now())
                                        .build()
                        )
                );

        Message message= Message.builder()
                .chat_id(chat)
                .status(messageDto.messageStatus())
                .created_at(LocalDateTime.now())
                .data(messageDto.data())
                .sender(sender)
                .build();

        messageRepository.save(message);
        return true;
    }

    public Boolean validLocation(LocationDto locationDto){
        if(!(locationDto.latitude()>=-90&&locationDto.latitude()<=90))return false;
        if(!(locationDto.longitude()>=-180&&locationDto.longitude()<=180))return false;
        Instant now=Instant.now();
        Instant requestedAt = locationDto.requested_at();

        Duration diff = Duration.between(requestedAt, now);

        if (diff.isNegative()) return false;
        if (diff.toSeconds() > 30) return false;

        return true;
    }

    public SliceResponse<com.spring_study.chatting.dto.response.MessageDto> getMessages(Long id,Integer pageNum,Integer pageSize){
        Chat chat=chatRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Chat not found"));

        Pageable pageable= PageRequest.of(
                pageNum,
                pageSize,
                Sort.by(Sort.Direction.DESC,"id")
        );

        Slice<Message> messageSlice=messageRepository.findAllByChatId(id,pageable);

        List<com.spring_study.chatting.dto.response.MessageDto> list=messageSlice.getContent().stream()
                .map(com.spring_study.chatting.dto.response.MessageDto::fromEntity)
                .toList();

        return SliceResponse.from(messageSlice,list);
    }

    public SliceResponse<com.spring_study.chatting.dto.response.MessageDto> searchMessage(MessageSearch messageSearch){
        Chat chat=chatRepository.findById(messageSearch.chatId()).orElseThrow(()->new IllegalArgumentException("Chat not found"));

        Message anchor=messageRepository.findFirstByChatIdAndIsDeleteFalseAndDataContainingOrderByIdDesc(messageSearch.chatId(), messageSearch.data())
                .orElseThrow(()->new IllegalArgumentException("Message not found"));

        Slice<Message> messageSlice=messageRepository.findAfter(chat.getId(),anchor.getId());
        List<com.spring_study.chatting.dto.response.MessageDto> list=messageSlice.getContent().stream()
                .map(com.spring_study.chatting.dto.response.MessageDto::fromEntity).toList();

        return SliceResponse.from(messageSlice,list);
    }

    public Boolean editMessage(Long id, MessageUpdate messageUpdate) {
        Message message=messageRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Message not found"));
        User sender=userRepository.findById(messageUpdate.sender()).orElseThrow(()->new IllegalArgumentException("Sender not found"));

        if(!message.getSender().equals(sender))return false;

        message.update(messageUpdate);
        return true;
    }

    public Boolean deleteMessage(Long id,Long userId){
        Message message=messageRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Message not found"));
        User sender=userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("Sender not found"));

        if(!message.getSender().equals(sender))return false;
        message.delete();
        return true;
    }


}
