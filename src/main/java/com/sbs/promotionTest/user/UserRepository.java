package com.sbs.promotionTest.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<_User, Long> {
    Optional<_User> findByNickname(String nickname);
    Optional<_User> findByEmail(String email);
}
