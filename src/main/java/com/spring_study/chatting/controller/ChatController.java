package com.spring_study.chatting.controller;

import com.spring_study.chatting.dto.request.MessageDto;
import com.spring_study.chatting.dto.response.ChatDto;
import com.spring_study.chatting.dto.response.SliceResponse;
import com.spring_study.chatting.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/{id}")
    public ResponseEntity<SliceResponse<ChatDto>> getChat(@PathVariable Long id,
                                                          @RequestParam(defaultValue = "0") Integer pageNum,
                                                          @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(chatService.getChat(id,pageNum,pageSize));
    }
}
