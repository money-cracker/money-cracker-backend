package com.cracker.money.src.user.entity;

import com.cracker.money.common.entity.BaseEntity;

import com.cracker.money.src.transaction.entity.Currency;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity
@Table(name = "account")
public class Account extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, name = "account_type")
    @ColumnDefault("0")
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_currency_id")
    private Currency defaultCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 20, name = "initial_balance")
    private BigDecimal initialBalance;

    @Column(nullable = false, length = 30, name = "current_balance")
    private BigDecimal currentBalance;

    @Column(nullable = false, name = "is_excluded_from_report")
    @ColumnDefault("false")
    private Boolean isExcludedFromReport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "account_status")
    @ColumnDefault("'ACTIVE'")
    private AccountStatus accountStatus;

    public enum AccountType {
        BASIC,
        CREDIT,
        LINKED,
        LOAN
    }
    public enum AccountStatus {
        ACTIVE,
        INACTIVE,
        CLOSED,
        SUSPENDED,
        LIMITED
    }

    @Builder
    public Account(Long id, String name, AccountType accountType, Currency defaultCurrency, User user,
                   BigDecimal initialBalance, BigDecimal currentBalance, Boolean isExcludedFromReport,
                   AccountStatus accountStatus
    ) {
        this.id = id;
        this.name = name;
        this.accountType = accountType;
        this.defaultCurrency = defaultCurrency;
        this.user = user;
        this.initialBalance = initialBalance;
        this.currentBalance = currentBalance;
        this.isExcludedFromReport = isExcludedFromReport;
        this.accountStatus = accountStatus;
    }

}
