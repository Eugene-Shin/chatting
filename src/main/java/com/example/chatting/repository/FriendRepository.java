package com.example.chatting.repository;

import com.example.chatting.domain.Friend;
import com.example.chatting.domain.User;
import com.example.chatting.domain.enums.FriendEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    //친구 리스트 조회, 친구 검색, 친구 차단, 친구 숨김
    // 친구 리스트 조회: id로 친구 리스트 조회
    //친구 검색: id로 친구 조회
    //친구 차단: id로 친구 차단
    //친구 숨김: id로 친구 숨김

    List<Friend> findFriendByUid(User uid); //유저id로 유저의 친구 리스트 조회
    Optional<Friend> findFriendByUidAndFid(User uid, User fid); //관계 확인

    //이름으로 친구 검색
    List<Friend> findByUidAndStatusAndFid_NameContaining(User uid, FriendEnum status, String name);
}
