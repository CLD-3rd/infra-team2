package com.team.infra_team2.user.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.user.constant.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Entity
public class User extends BaseEntity {
	
	@Id
	@Column(name = "user_id")
	private Long id;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private UserRole role;
	
}
