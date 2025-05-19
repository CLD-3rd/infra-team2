package com.team.infra_team2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team.infra_team2.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
