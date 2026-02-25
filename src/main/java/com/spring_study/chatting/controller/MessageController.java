package com.spring_study.chatting.controller;

import com.spring_study.chatting.dto.request.LocationDto;
import com.spring_study.chatting.dto.request.MessageDto;
import com.spring_study.chatting.dto.request.MessageSearch;
import com.spring_study.chatting.dto.request.MessageUpdate;
import com.spring_study.chatting.dto.response.SliceResponse;
import com.spring_study.chatting.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("")
    public ResponseEntity<Boolean> sendMessage(@RequestBody MessageDto messageDto){
        return ResponseEntity.ok(messageService.sendMessage(messageDto));
    }

    @PostMapping("/location")
    public ResponseEntity<Boolean> validLocation(@RequestBody LocationDto locationDto){
        return ResponseEntity.ok(messageService.validLocation(locationDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SliceResponse<com.spring_study.chatting.dto.response.MessageDto>> getMessages(@PathVariable Long id,
                                                                                                        @RequestParam(defaultValue = "0") Integer pageNum,
                                                                                                        @RequestParam(defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(messageService.getMessages(id,pageNum,pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<SliceResponse<com.spring_study.chatting.dto.response.MessageDto>> searchMessage(@RequestBody MessageSearch messageSearch){
        return ResponseEntity.ok(messageService.searchMessage(messageSearch));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Boolean> editMessage(@PathVariable Long id, @RequestBody MessageUpdate messageUpdate){
        return ResponseEntity.ok(messageService.editMessage(id,messageUpdate));
    }

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Boolean> deleteMessage(@PathVariable Long id,@PathVariable Long userId){
        return ResponseEntity.ok(messageService.deleteMessage(id,userId));
    }
}
