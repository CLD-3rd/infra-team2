package com.team.infra_team2.user.security.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.team.infra_team2.user.entity.User;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor

public class PrincipalDetails implements UserDetails {

//재정의해야하것 크개 3가지 1. 유저 이름 2. 유저 비번 3. 유저 권한

	private final User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
		authorites.add(() -> user.getUserRoleType().getRole());

		return authorites;
	}


	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

}
