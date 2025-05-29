package com.vata.profile.controller;

import com.vata.common.annotation.LoginUser;
import com.vata.profile.application.ProfilePromptFacade;
import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.controller.swagger.ProfileControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerSpec {

    private final ProfilePromptFacade profilePromptFacade;

    @PostMapping("/generate")
    @Override
    public ResponseEntity<?> generateImage(@LoginUser Long userId,
                                                               @RequestBody UserInputRequest request) {
        try {
            ImageGenerateResponse response = profilePromptFacade.generateProfileImage(userId, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
