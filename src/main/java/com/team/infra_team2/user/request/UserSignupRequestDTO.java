package com.team.infra_team2.user.request;

import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class UserSignupRequestDTO {


	@NotBlank(message = "아이디는 필수 입력값입니다.")
	@Pattern(regexp = "^[a-zA-Z0-9]{6,12}$", message = "아이디는 영문과 숫자 조합 6~12자여야 합니다.")
	private String username;
	@NotBlank(message = "비밀번호는 필수 입력값입니다.")
	@Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
	@Pattern(regexp = ".*[!@#$%^&*()_+=\\-\\[\\]{};':\"\\\\|,.<>/?].*", message = "비밀번호는 특수문자를 최소 1개 포함해야 합니다.")
	private String password;

	public User toEntity() {
		return User.of(username, password, UserRoleType.USER);
	}
}