package com.team.infra_team2.user.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.exception.CustomException;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.request.UserLoginRequestDTO;
import com.team.infra_team2.user.request.UserSignupRequestDTO;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;
import com.team.infra_team2.user.service.SecurityService;
import jakarta.validation.Valid;



@Controller
@RequestMapping("/api/auth")
public class SecurityController {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	private final SecurityService securityService;
	public SecurityController(UserRepository userRepository, PasswordEncoder passwordEncoder,
			AuthenticationManager authenticationManager, SecurityService securityService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.securityService = securityService;
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

		@PostMapping("/login")
		public String customLogin(@Valid @ModelAttribute UserLoginRequestDTO dto,
				BindingResult bindingResult, Model model) throws javax.naming.AuthenticationException {

			if (bindingResult.hasErrors()) {
		        model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
		        return "login";
		    }
			
			try {
				if (securityService.login(dto)) {
					String username = securityService.getCurrentUsername();
					model.addAttribute("username", username);
					return "redirect:/api/questions?page=1&size=10";
				}
			} catch (AuthenticationException e) {
				model.addAttribute("error", "로그인 실패: 자격 증명에 실패하였습니다.");
			}
			return "login";
			
		}
		
		

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		model.addAttribute("userSignupRequestDTO", new UserSignupRequestDTO());
		return "signUp";
	}


	@PostMapping("/signup")
	public String signUp(@Valid @ModelAttribute UserSignupRequestDTO requestDTO,
	                     BindingResult bindingResult,
	                     Model model) {
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("error", bindingResult.getAllErrors().get(0).getDefaultMessage());
	        model.addAttribute("userSignupRequestDTO", requestDTO);
	        return "signup";
	    }

	    try {
	        User user = securityService.signup(requestDTO);
	        model.addAttribute("username", user.getUsername());
	        return "redirect:/api/auth/login";
	    } catch (CustomException e) {
	        model.addAttribute("error", e.getMessage());
	        model.addAttribute("userSignupRequestDTO", requestDTO);
	        return "signup";
	    }
	}

//@PreAuthorize("hasRole('ROLE_USER)') or hasRole('ROLE_MANAGER')")
	@Secured(value = { "ROLE_USER", "ROLE_MANAGER" }) // 실행되기전에 권한점검
	@GetMapping("/about")
	public String aboutPage() { // 매소드에 권한부여

		return "about";
	}
}