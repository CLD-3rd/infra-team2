package com.team.infra_team2.user.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration 
@EnableWebSecurity 
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

@Bean 
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//1. CSRF
//2.CORS 자원공유 정책 . 다른곳에서 요청한다고해서 주면안됌. 기본은 SOP(내가내꺼사용 남은안됨. 내서버 내자원) -> origin 하나라도 다르면.. 다른주소로 인식하지...
/*
http://localhost:8080(origin) -> 요청 -> index.html 접근 가능 (SOP) [원래 이게 기본임 내꺼나만씀]
http://localhost:8081 -> 요청 -> index.html 접근여부 (CORS 정책: 허용해줄꺼니? 접근하게 해줄게~ 설정하면)
*/
//3. http.authorizeHttpRequests // -> 인가처리. 각각의 권한
//4. http.formLogin  //login.html 커스터마이징 로그인 페이지 이동?
//5. BCryptPasswordEncoder

http
.csrf(csrf -> csrf.disable()) //CSRF 실습에서는 진행하지않음. 보호하지않음. disable로 설정
.cors(cors -> cors.disable());


http
.authorizeHttpRequests(authorize ->
authorize
// .anyRequest().permitAll(); //모든 사람에게 접근가능 //여기서 기재 시 ROLE_부분은 빼준다 프리픽스 제거
.requestMatchers("/user/**").hasAnyRole("USER", "MANAGER", "ADMIN") //유저, 매니져, 어드민 접근가능한 user 페이지. //만약 권한없다면 USER페이지 대신 로그인페이지로 자동이동시킴
.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")//admin,매니져만 접근가능한
.requestMatchers("/admin/**").hasAnyRole("ADMIN") //admin만 접근가능한 페이지
.anyRequest().permitAll());

//인가에 매소드를 직접지정하는 방법 -> controller 에서 /about



http
.formLogin(form->
form
.loginPage("/login")//"/login"는 컨트롤러의 맵핑주소. 이제 커스터마이징한 로그인페이지 접근가능 시큐리티가 제공하는 로그인페이지말고.
.loginProcessingUrl("/login") //프론트 포스트요청 URI로부터 정보를 가로채와서 유저서비스쪽에서 처리해야함...이 Url설정으로부터. 가로채는 위치를 설정해주는 부분임
.defaultSuccessUrl("/"));  //로그인 성공시 어디로 이동할건지?-> 여기선 / 로 기본 인덱스로 가도록 설정



return http.build();

}


@Bean
public PasswordEncoder passwordEncoder(){ 
return new BCryptPasswordEncoder();
}

}