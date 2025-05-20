package com.team.infra_team2.question.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.team.infra_team2.question.dto.QuestionCreateRequestDTO;
import com.team.infra_team2.question.dto.QuestionCreateResponseDTO;
import com.team.infra_team2.question.service.QuestionService;
import com.team.infra_team2.user.entity.User;
import com.team.infra_team2.user.repository.UserRepository;
import com.team.infra_team2.user.security.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class QuestionController {
	
    // 서비스 주입
    private final QuestionService questionService;
    private final UserRepository userRepository;
    
    @GetMapping("/api/questions/form")
    @PreAuthorize("hasRole('ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("question", new QuestionCreateRequestDTO());
        return "question_form"; 
    }
    
    
    @PostMapping("/api/questions")
    @PreAuthorize("hasRole('ADMIN')")
    public String createQuestion(
    		@ModelAttribute QuestionCreateRequestDTO requestDTO,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    		){    
    	String username = principalDetails.getUsername();
    	User user = userRepository.findByUsername(username);
    	if (user == null) {
    	    throw new RuntimeException("유저가 없습니다");
    	}
    	QuestionCreateResponseDTO responseDTO = questionService.createQuestion(requestDTO, user);

        return "redirect:/questions";
    }
   
}
