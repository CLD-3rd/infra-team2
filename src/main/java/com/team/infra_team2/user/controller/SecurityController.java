package com.team.infra_team2.user.controller;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.request.UserLoginRequestDTO;
import com.team.infra_team2.user.request.UserSignupRequestDTO;
import com.team.infra_team2.user.response.UserLoginResponseDTO;
import com.team.infra_team2.user.response.UserSignupResponseDTO;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;



@Controller
@RequestMapping("/api/auth")
public class SecurityController {

	private final UserRepository userRepository;
// 암호화 모듈 주입
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	public SecurityController(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
	}

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

	@PostMapping(value = "/login", produces = "application/json")
	@ResponseBody
	public ResponseEntity<?> customlogin(@RequestBody UserLoginRequestDTO dto) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

			// 인증 성공한 객체를 SecurityContext에 수동으로 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);

			// PrincipalDetails에서 유저 정보 가져오기
			PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

			UserLoginResponseDTO responseDTO = UserLoginResponseDTO.of(principal.getUsername());

			return ResponseEntity.ok(responseDTO);

		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).body(Map.of("message", "로그인 실패: 자격 증명에 실패하였습니다."));
		}
	}

	@GetMapping("/signup")
	public String signUpPage() {

		return "signUp";
	}

	@PostMapping(value = "/signup", produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserSignupResponseDTO> signUp(@RequestBody UserSignupRequestDTO requestDTO) {

		String rawPassword = requestDTO.getPassword();
		String encryptedPassword = passwordEncoder.encode(rawPassword);

		User user = requestDTO.toEntity();
		user.setPassword(encryptedPassword);

		userRepository.save(user);

		UserSignupResponseDTO responseDTO = UserSignupResponseDTO.of(user.getUsername());
		return ResponseEntity.ok(responseDTO);
	}

//@PreAuthorize("hasRole('ROLE_USER)') or hasRole('ROLE_MANAGER')")
	@Secured(value = { "ROLE_USER", "ROLE_MANAGER" }) // 실행되기전에 권한점검
	@GetMapping("/about")
	public String aboutPage() { // 매소드에 권한부여

		return "about";
	}
}