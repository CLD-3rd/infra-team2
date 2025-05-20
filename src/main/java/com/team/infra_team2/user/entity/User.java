package com.team.infra_team2.user.entity;

import com.team.infra_team2.common.entity.BaseEntity;
import com.team.infra_team2.user.constant.UserRoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;

    protected User() {
        // JPA 기본 생성자
    }

    private User(String username, String password, UserRoleType userRoleType) {
        this.username = username;
        this.password = password;
        this.userRoleType = userRoleType;
    }

    public static User of(String username, String password, UserRoleType userRoleType) {
        return new User(username, password, userRoleType);
    }
}
