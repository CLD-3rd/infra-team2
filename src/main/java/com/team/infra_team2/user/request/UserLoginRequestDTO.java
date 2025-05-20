package com.team.infra_team2.user.request;

import lombok.Getter;
import lombok.ToString;
@ToString
@Getter
public class UserLoginRequestDTO {

	private String username;
	private String password;
}