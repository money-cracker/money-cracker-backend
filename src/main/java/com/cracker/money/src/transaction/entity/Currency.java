package com.cracker.money.src.transaction.entity;

import com.cracker.money.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity
@Table(name = "currency")
public class Currency extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150, unique = true)
    private String country;

    @Column(nullable = false, length = 50, unique = true)
    private String code;

}
