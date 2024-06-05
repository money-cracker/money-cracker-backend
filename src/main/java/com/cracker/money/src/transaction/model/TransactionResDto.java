package com.cracker.money.src.transaction.model;


import com.cracker.money.src.transaction.entity.Currency;
import com.cracker.money.src.transaction.entity.Transaction;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResDto {
    //    transaction_category_id
    //    parent_transaction_id
    //    account_id

    private Long id;
    private Boolean isRecurring;
    private Currency currency;
    private BigDecimal effectiveAmount;
    private LocalDateTime effectiveDate;
    private String detail;
    private String location;
    private String attached_image_url;
    private Boolean isExcludedFromReport;
    public TransactionResDto(Transaction transaction) {
        this.id = transaction.getId();
        this.isRecurring = transaction.getIsRecurring();
        this.currency = transaction.getCurrency();
        this.effectiveAmount = transaction.getEffectiveAmount();
        this.effectiveDate = transaction.getEffectiveDate();
        this.detail = transaction.getDetail();
        this.location = transaction.getLocation();
        this.attached_image_url = transaction.getAttachedImageUrl();
        this.isExcludedFromReport = transaction.getIsExcludedFromReport();
    }
}
