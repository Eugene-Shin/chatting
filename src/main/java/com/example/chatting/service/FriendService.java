package com.example.chatting.service;

import com.example.chatting.domain.Friend;
import com.example.chatting.domain.User;
import com.example.chatting.domain.enums.FriendEnum;
import com.example.chatting.dto.response.FriendListResponseDto;
import com.example.chatting.repository.FriendRepository;
import com.example.chatting.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //친구 리스트 조회
    public List<FriendListResponseDto> getFriendList(UUID uid) {
        User user = userRepository.findUsersByUuid(uid);
        List<Friend> friendList = friendRepository.findFriendByUid(user);

        List<FriendListResponseDto> list = new ArrayList<>();
        for(Friend friend : friendList) {
            if(friend.getStatus() == FriendEnum.NORMAL) {
                FriendListResponseDto dto = FriendListResponseDto.fromEntity(friend);
                list.add(dto);
            }
        }

        return list;
    }

    //친구 검색 - 이름으로 검색해야함
    public List<FriendListResponseDto> searchFriend(UUID uid, String name) {
        User user = userRepository.findUsersByUuid(uid);
        List<Friend> list = friendRepository.findByUidAndStatusAndFid_NameContaining(user, FriendEnum.NORMAL, name);

        if(list.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다. ");
        }

        List<FriendListResponseDto> dtoList = list.stream()
                .map(FriendListResponseDto::fromEntity)
                .toList();

        return dtoList;
    }

    //친구 차단
    public FriendListResponseDto blockFriend(UUID uid, UUID fid) {
        User user = userRepository.findUsersByUuid(uid);
        User friend = userRepository.findUsersByUuid(fid);

        Friend isFriend = friendRepository.findFriendByUidAndFid(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구가 아닙니다. "));

        if(isFriend.getStatus() == FriendEnum.BLOCKED) {
            isFriend.updateStatus(FriendEnum.NORMAL);
        } else isFriend.updateStatus(FriendEnum.BLOCKED);

        return FriendListResponseDto.fromEntity(isFriend);
    }

    //친구 숨김
    public FriendListResponseDto hideFriend(UUID uid, UUID fid) {
        User user = userRepository.findUsersByUuid(uid);
        User friend = userRepository.findUsersByUuid(fid);

        Friend isFriend = friendRepository.findFriendByUidAndFid(user, friend)
                .orElseThrow(() -> new IllegalArgumentException("친구가 아닙니다. "));

        if(isFriend.getStatus() == FriendEnum.HIDDEN) {
            isFriend.updateStatus(FriendEnum.NORMAL);
        } else isFriend.updateStatus(FriendEnum.HIDDEN);
        return FriendListResponseDto.fromEntity(isFriend);
    }

    //숨김/차단 친구 리스트
    public List<FriendListResponseDto> getBlockFriendList(UUID uid) {
        User user = userRepository.findUsersByUuid(uid);
        List<Friend> list = friendRepository.findFriendByUid(user);

        List<FriendListResponseDto> blockList = new ArrayList<>();
        for(Friend friend : list) {
            if(friend.getStatus() == FriendEnum.HIDDEN || friend.getStatus() == FriendEnum.BLOCKED) {
                FriendListResponseDto dto = FriendListResponseDto.fromEntity(friend);
                blockList.add(dto);
            }
        }
        return blockList;
    }
}
