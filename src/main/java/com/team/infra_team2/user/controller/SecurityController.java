package com.team.infra_team2.user.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;


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


@GetMapping("/signup")
public String signUpPage() {

return "signUp";
}

@PostMapping("/signup")
public String signUp(User user) {

 

//비밀번호 암호화
String rawPassword=user.getPassword(); 
System.out.println(rawPassword);
String encrptedOassword = passwordEncoder.encode(rawPassword);
System.out.println(encrptedOassword);


//객체 저장 중
user.setPassword(encrptedOassword);
user.setUserRoleType(UserRoleType.ADMIN); 
userRepository.save(user);

return "redirect:/";
}


//@PreAuthorize("hasRole('ROLE_USER)') or hasRole('ROLE_MANAGER')")
@Secured(value= {"ROLE_USER", "ROLE_MANAGER"}) //실행되기전에 권한점검
@GetMapping("/about")
public String aboutPage() { //매소드에 권한부여

return "about";
}
}