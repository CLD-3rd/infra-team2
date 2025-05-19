package com.team.infra_team2.user.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.user.constant.UserRoleType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Entity
@Setter
public class User extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	
	private String username;
	
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRoleType user_role_type;
	
	public User() {} 

    private User(String username, String password, UserRoleType userRoleType) {
        this.username = username;
        this.password = password;
        this.user_role_type = userRoleType;
    }

    public static User of(String username, String password, UserRoleType userRoleType) {
        return new User(username, password, userRoleType);
    }
	
}