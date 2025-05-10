package com.vata.profile.controller;

import com.vata.profile.application.ProfilePromptFacade;
import com.vata.profile.controller.dto.UserInputRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfilePromptFacade profilePromptFacade;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePrompt(@RequestBody UserInputRequest request) {
        String prompt = profilePromptFacade.generatePrompt(request);
        // TODO: 이미지 생성
        return ResponseEntity.ok(prompt);
    }
}
