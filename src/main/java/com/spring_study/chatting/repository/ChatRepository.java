package com.spring_study.chatting.repository;

import com.spring_study.chatting.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    @Query("""
    SELECT c 
    FROM Chat c 
    WHERE c.uid1.id = :userId 
       OR c.uid2.id = :userId
    ORDER BY c.created_at DESC
""")
    Slice<Chat> findAllByUserId(@Param("userId") Long userId, Pageable pageable);
    @Query("""
    SELECT c FROM Chat c
    WHERE (c.uid1.id = :user1 AND c.uid2.id = :user2)
       OR (c.uid1.id = :user2 AND c.uid2.id = :user1)
""")
    Optional<Chat> findChatByUsers(Long user1, Long user2);
}
