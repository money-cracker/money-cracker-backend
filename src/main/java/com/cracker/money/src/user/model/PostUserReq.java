package com.cracker.money.src.user.model;

import com.cracker.money.src.user.entity.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostUserReq {
    @NotBlank(message = "Please enter email address")
    @Email(message = "Invalid email format")
    private String email;

    private String password;

    @NotBlank(message = "Please enter user name")
    @Pattern(regexp = "^[a-z0-9._]{1,20}$", message = "Username can only include string, number, _, and .")
    private String userName;

    private String mobile;
    private LocalDate birthday;
    private String gender;
    private String profileImageUrl;
    private Boolean isOAuth = false;
    private User.Role role = User.Role.GENERAL;
    private User.UserStatus userStatus = User.UserStatus.ACTIVE;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .userName(this.userName)
                .mobile(this.mobile)
                .birthday(this.birthday)
                .gender(this.gender)
                .profileImageUrl(this.profileImageUrl)
                .isOAuth(this.isOAuth)
                .isPublic(true)
                .role(this.role)
                .userStatus(this.userStatus)
                .build();
    }
}