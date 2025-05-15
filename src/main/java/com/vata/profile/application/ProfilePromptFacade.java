package com.vata.profile.application;

import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.domain.entity.UserInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePromptFacade {

    public String generatePrompt(UserInputRequest request) {
        UserInput input = UserInput.of(request);
        return input.generatePrompt();
    }
}
