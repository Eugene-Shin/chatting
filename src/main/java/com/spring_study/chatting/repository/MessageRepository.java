package com.spring_study.chatting.repository;

import com.spring_study.chatting.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    Slice<Message> findAllByChatId(Long id, Pageable pageable);
    Optional<Message> findFirstByChatIdAndIsDeleteFalseAndDataContainingOrderByIdDesc(
            Long chatId,
            String keyword
    );
    @Query("""
    select m from Message m
    where m.chat.id = :chatId
    and m.isDelete = false
    and m.id >= :anchorId
    order by m.id asc
""")
    Slice<Message> findAfter(Long chatId,Long anchorId);
}
