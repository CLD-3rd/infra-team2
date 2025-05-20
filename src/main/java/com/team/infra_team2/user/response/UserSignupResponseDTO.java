package com.team.infra_team2.user.response;

import lombok.Getter;


@Getter
public class UserSignupResponseDTO {

	private String username;
	private String message;

	public UserSignupResponseDTO(String username, String message) {
		this.username = username;
		this.message = message;
	}

	public static UserSignupResponseDTO of(String username) {
		return new UserSignupResponseDTO(username, "회원가입이 완료되었습니다.");
	}
}