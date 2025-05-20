package com.team.infra_team2.user.request;

import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class UserSignupRequestDTO {

	private String username;
	private String password;

	public User toEntity() {
		return User.of(username, password, UserRoleType.USER);
	}
}