package com.example.chatting.controller;

import com.example.chatting.domain.enums.FriendEnum;
import com.example.chatting.dto.response.FriendListResponseDto;
import com.example.chatting.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friends")
public class FriendController {
    private final FriendService friendService;

    @GetMapping("")
    public ResponseEntity<List<FriendListResponseDto>> getFriendList(UUID uid) {
        return ResponseEntity.ok(friendService.getFriendList(uid));
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<FriendListResponseDto>> searchFriend(@PathVariable String name, UUID uid) {
        return ResponseEntity.ok(friendService.searchFriend(uid, name));
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<FriendListResponseDto> blockFriend(@PathVariable UUID id, UUID uid) {
        return ResponseEntity.ok(friendService.blockFriend(uid, id));
    }

    @PatchMapping("/{id}/hide")
    public ResponseEntity<FriendListResponseDto> hideFriend(@PathVariable UUID id, UUID uid) {
        return ResponseEntity.ok(friendService.hideFriend(uid, id));
    }

    @GetMapping("/blacklist")
    public ResponseEntity<List<FriendListResponseDto>> getBlockFriendList(UUID uid) {
        return ResponseEntity.ok(friendService.getBlockFriendList(uid));
    }

}
