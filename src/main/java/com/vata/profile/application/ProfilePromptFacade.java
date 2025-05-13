package com.vata.profile.application;

import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.domain.entity.UserInput;
import com.vata.profile.domain.service.StabilityImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePromptFacade {
    private final StabilityImageService stabilityImageService;

    public ImageGenerateResponse generateProfileImage(UserInputRequest request) {
        String prompt = generatePrompt(request);
        return stabilityImageService.generateImage(1L, prompt, request.styleType()); // TODO: userId 변경
    }

    private String generatePrompt(UserInputRequest request) {
        UserInput input = UserInput.of(request);
        return input.generatePrompt();
    }
}
