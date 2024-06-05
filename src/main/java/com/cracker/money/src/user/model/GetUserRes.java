package com.cracker.money.src.user.model;


import com.cracker.money.common.entity.BaseEntity;
import com.cracker.money.src.user.entity.User;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private Long id;
    private String email;
    private String fullName;
    private String mobile;
    private String userName;
    private Boolean isOAuth;
    private String gender;
    private String profileImageUrl;
    private String website;
    private LocalDate birthday;
    private Boolean isPublic;
    private User.Role role;
    private User.UserStatus userStatus;
    protected BaseEntity.Status status;

    public GetUserRes(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.mobile = user.getMobile();
        this.userName = user.getUserName();
        this.gender = user.getGender();
        this.profileImageUrl = user.getProfileImageUrl();
        this.birthday = user.getBirthday();
        this.isPublic = user.getIsPublic();
        this.userStatus = user.getUserStatus();
        this.status = user.getStatus();
    }
}
