package com.cracker.money.src.transaction;
import com.cracker.money.common.entity.BaseEntity;
import com.cracker.money.src.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByStatus(BaseEntity.Status status);
//    List<Transaction> findAllByUserIdAndStatus(Long userId, BaseEntity.Status status);
}

