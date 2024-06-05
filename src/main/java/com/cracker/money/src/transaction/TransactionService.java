package com.cracker.money.src.transaction;


import com.cracker.money.src.transaction.model.TransactionResDto;
import com.cracker.money.src.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cracker.money.common.entity.BaseEntity.Status.ACTIVE;

@Transactional
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public List<TransactionResDto> getTransactionsByUsers(Long userId) {
//        return transactionRepository.findAllByUserIdAndStatus(userId, ACTIVE).stream()
//                .map(TransactionResDto::new)
//                .collect(Collectors.toList());
//
        return transactionRepository.findAllByStatus(ACTIVE).stream()
                .map(TransactionResDto::new)
                .collect(Collectors.toList());
    }

}
