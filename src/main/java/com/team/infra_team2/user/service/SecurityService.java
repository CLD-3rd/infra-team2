package com.team.infra_team2.user.service;

import javax.naming.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.exception.CustomException;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.request.UserLoginRequestDTO;
import com.team.infra_team2.user.request.UserSignupRequestDTO;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

@Service
public class SecurityService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public SecurityService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

	public boolean login(UserLoginRequestDTO dto) throws AuthenticationException {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return true;
		} catch (UsernameNotFoundException e) {
			throw new CustomException("존재하지 않는 아이디입니다.", HttpStatus.NOT_FOUND);
		} catch (BadCredentialsException e) {
			throw new CustomException("비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED);
		}
	}

	public User signup(UserSignupRequestDTO requestDTO) {
		// 유저 중복 체크
		if (userRepository.findByUsername(requestDTO.getUsername()) != null) {
			throw new CustomException("이미 존재하는 아이디입니다.");
		}

		String rawPassword = requestDTO.getPassword();
		String encryptedPassword = passwordEncoder.encode(rawPassword);

		User user = requestDTO.toEntity();
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		return userRepository.save(user);
	}

	public String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof PrincipalDetails principal) {
			return principal.getUsername();
		}
		return null;
	}

}
