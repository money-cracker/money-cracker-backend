package com.cracker.money.src.transaction.entity;

import com.cracker.money.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity
@Table(name = "transaction")
public class Transaction extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO :: 테이블 추가 후 아래 컬럼들 마저 추가하기
    //    transaction_category_id
    //    parent_transaction_id
    //    account_id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id")
    private Currency currency;

    @Column(nullable = false, name = "is_recurring")
    @ColumnDefault("false")
    private Boolean isRecurring;

    @Column(nullable = false, name = "effective_amount")
    @ColumnDefault(value = "0")
    private BigDecimal effectiveAmount;

    @Column(name = "effective_date")
    private LocalDateTime effectiveDate;

    @Column(length = 200)
    private String detail;

    @Column(length = 100)
    private String location;


    @Column(length = 500, name = "attached_image_url")
    private String attachedImageUrl;

    @Column(nullable = false, name = "is_excluded_from_report")
    @ColumnDefault("false")
    private Boolean isExcludedFromReport;

//    transaction_category_id
//    parent_transaction_id
//    is_recurring
//    account_id
//    currency_id
//    effective_amount
//    effective_date
//    detail
//    location
//    attached_image_url
//    is_excluded_from_report
}
