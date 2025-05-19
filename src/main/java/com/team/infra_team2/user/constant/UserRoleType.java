package com.team.infra_team2.user.constant;

import lombok.Getter;

@Getter
public enum UserRoleType {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");
	
	private String role;
	
	UserRoleType(String role) {
		this.role = role;
	}
}
