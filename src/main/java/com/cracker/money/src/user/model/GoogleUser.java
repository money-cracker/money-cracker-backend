package com.cracker.money.src.user.model;

import com.cracker.money.src.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class GoogleUser {
    public String id;
    public String email;
    public Boolean verifiedEmail;
    public String name;
    public String picture;
    private User.Role role = User.Role.GENERAL;
    private User.UserStatus userStatus = User.UserStatus.ACTIVE;

    public User toEntity() {
        return User.builder()
                .email(this.email)
                .password("NONE")
                .userName(this.name)
                .isOAuth(true)
                .isPublic(true)
                .profileImageUrl(this.picture)
                .provider(User.Provider.GOOGLE)
                .providerId(this.id)
                .role(this.role)
                .userStatus(this.userStatus)
                .build();
    }
}
