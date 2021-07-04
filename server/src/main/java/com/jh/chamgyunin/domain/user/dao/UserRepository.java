package com.jh.chamgyunin.domain.user.dao;

import com.jh.chamgyunin.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(final String email);
}
