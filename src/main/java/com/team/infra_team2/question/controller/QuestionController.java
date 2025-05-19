package com.team.infra_team2.question.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team.infra_team2.question.dto.QuestionCreateRequestDTO;
import com.team.infra_team2.question.dto.QuestionCreateResponseDTO;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.user.constant.UserRoleType;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    // 서비스 주입
    private final QuestionService questionService;
    private final UserRepository userRepository;
    @PostMapping
    public ResponseEntity<QuestionCreateResponseDTO> createQuestion(
            @RequestBody QuestionCreateRequestDTO requestDTO
            // TODO: 로그인 구현 완료 시 아래 파라미터 복구
            // , @AuthenticationPrincipal UserDetails userDetails
    ) {
        // TODO: 로그인 구현 완료 시, CustomUserDetails에서 유저 꺼내서 아래 주입
        // CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
        // User user = customUserDetails.getUser();

        // [임시 유저 객체 생성 - 테스트용]
        User user = new User(); // 기본 생성자는 protected → 필요한 경우 임시 생성자 만들거나 setter 활용
        user.setUsername("mock_admin");
        user.setPassword("test1234");
        user.setUser_role_type(UserRoleType.ADMIN);

        user = userRepository.save(user);

        QuestionCreateResponseDTO responseDTO = questionService.createQuestion(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }
}
