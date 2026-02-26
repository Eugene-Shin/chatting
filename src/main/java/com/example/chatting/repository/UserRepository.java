package com.example.chatting.repository;

import com.example.chatting.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUsersByUuid(UUID uuid);
}
