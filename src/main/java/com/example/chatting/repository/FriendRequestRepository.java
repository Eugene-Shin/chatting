package com.example.chatting.repository;

import com.example.chatting.domain.FriendRequest;
import com.example.chatting.domain.User;
import com.example.chatting.domain.enums.FriendRequestEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    //친구 요청
    //친구 요청 수락
    //친구 요청 거절

    //친구 요청을 보내려면 "이미 보낸 상대인지" 확인 필요
    boolean existsByRequesterAndRespondent(User requester, User respondent);

    //받은 요청 리스트
    List<FriendRequest> findByRespondentAndStatus(User respondent, FriendRequestEnum status);

    //수락/거절
    Optional<FriendRequest> findById(Long id);
}
