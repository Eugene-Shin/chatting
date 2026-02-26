package com.example.chatting.controller;

import com.example.chatting.domain.FriendRequest;
import com.example.chatting.domain.enums.FriendRequestEnum;
import com.example.chatting.dto.request.FriendRequestDto;
import com.example.chatting.dto.response.FriendRequestResponseDto;
import com.example.chatting.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/friend-requests")
public class FriendRequestController {
    private final FriendRequestService friendRequestService;

    @PostMapping("")
    public ResponseEntity<String> sendFriend(@RequestBody FriendRequestDto friendRequestDto, UUID uid){
        friendRequestService.sendFriend(uid, friendRequestDto);
        return ResponseEntity.ok("친구 요청 전송 성공");
    }

    @GetMapping("")
    public ResponseEntity<List<FriendRequestResponseDto>> requestFriendList(UUID uid) {
        return ResponseEntity.ok(friendRequestService.requestFriendList(uid));
    }

    @PatchMapping("/{requestId}/accept")
    public ResponseEntity<String> acceptRequest(@PathVariable Long requestId, UUID uid) {
        friendRequestService.acceptRequest(uid, requestId, FriendRequestEnum.ACCEPT);
        return ResponseEntity.ok("친구 요청 수락");
    }

    @PatchMapping("/{requestId}/decline")
    public ResponseEntity<String> declineRequest(@PathVariable Long requestId, UUID uid) {
        friendRequestService.acceptRequest(uid, requestId, FriendRequestEnum.DECLINE);
        return ResponseEntity.ok("친구 요청 거절");
    }

}
