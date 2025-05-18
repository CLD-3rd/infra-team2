package com.team.infra_team2.user.entity;

import com.team.infra_team2.user.constant.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
public class User {
	
	@Id
	@Column(name = "user_id")
	private Integer id;
	
	private String password;
	
	private UserRole role;
	
}
