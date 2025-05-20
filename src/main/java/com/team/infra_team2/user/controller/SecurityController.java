package com.team.infra_team2.user.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import com.team.infra_team2.user.request.UserLoginRequestDTO;
import com.team.infra_team2.user.request.UserSignupRequestDTO;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;



@RequiredArgsConstructor
@Controller
public class SecurityController {

private final UserRepository userRepository;
// 암호화 모듈 주입
private final PasswordEncoder passwordEncoder;

@GetMapping({ "", "/" })
public String indexPage() {
return "index";
}

@GetMapping("/user")
public String userPage() {
return "user";
}

@GetMapping("/admin")
public String adminPage() {
return "admin";
}

@GetMapping("/manager")
public String manager() {
return "manager";
}

@GetMapping("/login")
public String loginPage() {
return "login";
}

	// model and view
	@PostMapping("/login")
	public String customLogin(@ModelAttribute UserLoginRequestDTO dto, Model model) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
			model.addAttribute("username", principal.getUsername());

			return "redirect:/api/questions?page=1&size=10"; // 로그인 성공시 이동
		} catch (AuthenticationException e) {
			model.addAttribute("error", "로그인 실패: 자격 증명에 실패하였습니다.");
			return "login"; // 다시 로그인 페이지로
		}
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("userSignupRequestDTO", new UserSignupRequestDTO());
		return "signUp";
	}


	@PostMapping("/signup")
	public String signUp(@ModelAttribute UserSignupRequestDTO requestDTO, Model model) {
		String rawPassword = requestDTO.getPassword();
		String encryptedPassword = passwordEncoder.encode(rawPassword);

		User user = requestDTO.toEntity();
		user.setPassword(encryptedPassword);
		userRepository.save(user);

		model.addAttribute("username", user.getUsername());
		return "redirect:/api/auth/login"; // 회원가입 성공 후 로그인 페이지로 리다이렉트
	}

//@PreAuthorize("hasRole('ROLE_USER)') or hasRole('ROLE_MANAGER')")
@Secured(value= {"ROLE_USER", "ROLE_MANAGER"}) //실행되기전에 권한점검
@GetMapping("/about")
public String aboutPage() { //매소드에 권한부여

return "about";
}
}