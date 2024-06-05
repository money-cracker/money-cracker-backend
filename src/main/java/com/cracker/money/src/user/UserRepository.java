package com.cracker.money.src.user;
import com.cracker.money.common.entity.BaseEntity;
import com.cracker.money.src.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailAndStatus(String email, BaseEntity.Status status);
    Optional<User> findByEmailAndStatus(String email, BaseEntity.Status status);
    Optional<User> findByIdAndStatus(Long id, BaseEntity.Status status);
}

