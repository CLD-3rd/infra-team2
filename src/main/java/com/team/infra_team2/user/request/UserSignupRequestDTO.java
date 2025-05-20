package com.team.infra_team2.user.request;

import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;

public class UserSignupRequestDTO {

    private String username;
    private String password;

    public User toEntity() {
        return User.of(
            username,
            password,
            UserRoleType.USER
        );
    }
}