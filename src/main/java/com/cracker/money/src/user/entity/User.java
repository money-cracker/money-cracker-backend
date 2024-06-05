package com.cracker.money.src.user.entity;

import com.cracker.money.common.entity.BaseEntity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@ToString
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String mobile;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "is_oauth")
    @ColumnDefault("false")
    private Boolean isOAuth;

    @Column(length = 30)
    private String gender;

    @Column(length = 500, name = "profile_image_url")
    private String profileImageUrl;

    @Column
    private LocalDate birthday;

    @Column
    @ColumnDefault("true")
    private Boolean isPublic;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'GENERAL'")
    private User.Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "user_status")
    @ColumnDefault("'PENDING'")
    private User.UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private Provider provider;

    @Column(name = "provider_id")
    private String providerId;

    public enum Role {
        ADMIN, GENERAL
    }

    public enum UserStatus {
        PENDING, ACTIVE, BLOCKED, DORMANT
    }

    public enum Provider {
        LOCAL, GOOGLE
    }

    @Builder
    public User(Long id, String email, String mobile, String password, String userName,
                String gender, String profileImageUrl, LocalDate birthday, Role role,
                UserStatus userStatus, Boolean isPublic, Boolean isOAuth, Provider provider, String providerId
    ) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.profileImageUrl = profileImageUrl;
        this.birthday = birthday;
        this.role = role;
        this.userStatus = userStatus;
        this.isPublic = isPublic;
        this.isOAuth = isOAuth;
        this.provider = provider;
        this.providerId = providerId;
    }

}
