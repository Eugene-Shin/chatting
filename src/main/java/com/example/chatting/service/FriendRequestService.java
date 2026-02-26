package com.example.chatting.service;

import com.example.chatting.domain.Friend;
import com.example.chatting.domain.FriendRequest;
import com.example.chatting.domain.User;
import com.example.chatting.domain.enums.FriendEnum;
import com.example.chatting.domain.enums.FriendRequestEnum;
import com.example.chatting.dto.request.FriendRequestDto;
import com.example.chatting.dto.response.FriendListResponseDto;
import com.example.chatting.dto.response.FriendRequestResponseDto;
import com.example.chatting.repository.FriendRepository;
import com.example.chatting.repository.FriendRequestRepository;
import com.example.chatting.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendRequestService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;
    private final FriendRequestRepository friendRequestRepository;

    //친구 요청 전송
    public void sendFriend(UUID uid, FriendRequestDto friendRequestDto) {
        User requester = userRepository.findUsersByUuid(uid);
        User respondent = userRepository.findUsersByUuid(friendRequestDto.respondent());

        if(friendRepository.findFriendByUidAndFid(requester, respondent).isPresent()) {
            throw new IllegalArgumentException("이미 친구 관계입니다. ");
        }

        if(friendRequestRepository.existsByRequesterAndRespondent(requester, respondent )) {
            throw new IllegalArgumentException("이미 친구 요청을 보냈습니다. ");
        }

        friendRequestRepository.save(friendRequestDto.toEntity(requester, respondent));
    }

    //받은 요청 리스트 조회
    public List<FriendRequestResponseDto> requestFriendList(UUID uid) {
        User user = userRepository.findUsersByUuid(uid);
        List<FriendRequest> requestList = friendRequestRepository.findByRespondentAndStatus(user, FriendRequestEnum.PENDING);
        List<FriendRequestResponseDto> requestDtoList = requestList.stream()
                .map(FriendRequestResponseDto::fromEntity)
                .toList();
        return requestDtoList;

    }

    //받은 요청 수락, 거절
    public void acceptRequest(UUID uid, Long requestId, FriendRequestEnum now) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 친구 요청"));

        if(!friendRequest.getRespondent().getUuid().equals(uid)) {
            throw new IllegalArgumentException("요청 수락 권한 없음");
        }

        if(friendRequest.getStatus()!=FriendRequestEnum.PENDING) {
            throw new IllegalArgumentException("이미 처리된 요청");
        }

        if(now == FriendRequestEnum.ACCEPT) {
            friendRequest.updateStatus(FriendRequestEnum.ACCEPT);

            Friend me = Friend.builder()
                    .uid(friendRequest.getRespondent()) //나
                    .fid(friendRequest.getRequester()) //너
                    .status(FriendEnum.NORMAL)
                    .build();

            Friend you = Friend.builder()
                    .uid(friendRequest.getRequester()) //너
                    .fid(friendRequest.getRespondent()) //나(유저 본인)
                    .status(FriendEnum.NORMAL)
                    .build();

            friendRepository.save(me);
            friendRepository.save(you);
        } else {
            friendRequest.updateStatus(FriendRequestEnum.DECLINE);
        }
    }

}
