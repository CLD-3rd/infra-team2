package com.team.infra_team2.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//1. CSRF
//2.CORS 자원공유 정책 
//3. http.authorizeHttpRequests // -> 인가처리. 각각의 권한
//4. http.formLogin  //login.html 커스터마이징 로그인 페이지 이동
//5. BCryptPasswordEncoder

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());


		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/api/auth/user/**").hasAnyRole("USER", "MANAGER", "ADMIN") // 유저, 매니져, 어드민 접근가능한 user
				.requestMatchers("/api/auth/manager/**").hasAnyRole("MANAGER", "ADMIN")// admin,매니져만 접근가능한
				.requestMatchers("/api/auth/admin/**").hasAnyRole("ADMIN") // admin만 접근가능한 페이지
				.anyRequest().permitAll())
				.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()) // 403 처리
				);


		http.formLogin(form -> form.loginPage("/api/auth/login") // "/login"는 컨트롤러의 맵핑주소. 이제 커스터마이징한 로그인페이지 접근가능 시큐리티가
																	// 제공하는 로그인페이지말고.
				.loginProcessingUrl("/api/auth/login") // 프론트 포스트요청 URI로부터 정보를 가로채와서 유저서비스쪽에서 처리해야함...이 Url설정으로부터. 가로채는
														// 위치를 설정해주는 부분임
//.successHandler(authenticationSuccessHandler()) //성공 핸들러 등록
//.failureHandler(authenticationFailureHandler()) //실패 핸들러 등록
				.defaultSuccessUrl("/")); // 로그인 성공시 이동

		return http.build();

	}
	/*
	 * TEST 중...
	 * 
	 * @Bean public AuthenticationSuccessHandler authenticationSuccessHandler() {
	 * return (request, response, authentication) -> { PrincipalDetails principal =
	 * (PrincipalDetails) authentication.getPrincipal();
	 * 
	 * // 응답 객체 생성 UserLoginResponseDTO responseDTO = new UserLoginResponseDTO();
	 * responseDTO.setUsername(principal.getUsername());
	 * responseDTO.setMessage("로그인 성공"); // 필요한 추가 정보도 세팅 가능
	 * 
	 * response.setStatus(HttpServletResponse.SC_OK);
	 * response.setContentType("application/json;charset=UTF-8");
	 * 
	 * // JSON 응답 전송 ObjectMapper mapper = new ObjectMapper();
	 * response.getWriter().write(mapper.writeValueAsString(responseDTO)); }; }
	 * 
	 * 
	 * @Bean public AuthenticationFailureHandler authenticationFailureHandler() {
	 * return (request, response, exception) -> {
	 * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	 * response.setContentType("application/json;charset=UTF-8");
	 * 
	 * Map<String, String> error = new HashMap<>(); error.put("message", "로그인 실패: "
	 * + exception.getMessage());
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * response.getWriter().write(mapper.writeValueAsString(error)); }; }
	 */

	@Bean
	public AccessDeniedHandler customAccessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			response.sendRedirect("/api/auth/"); // 권한 없을 때 index로 리다이렉트
		};
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}