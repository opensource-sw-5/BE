package com.vata.profile.controller;

import com.vata.common.annotation.LoginUser;
import com.vata.profile.application.ProfilePromptFacade;
import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.StabilityCreditsResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.controller.swagger.ProfileControllerSpec;
import com.vata.profile.domain.service.StabilityBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerSpec {

    private final ProfilePromptFacade profilePromptFacade;
    private final StabilityBalanceService stabilityBalanceService; // StabilityAccountService 주입


    @PostMapping("/generate")
    @Override
    public ResponseEntity<ImageGenerateResponse> generateImage(@LoginUser Long userId,
                                                               @RequestBody UserInputRequest request) {
        ImageGenerateResponse response = profilePromptFacade.generateProfileImage(userId, request);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/credits")
    public ResponseEntity<StabilityCreditsResponse> getUserCredits(@LoginUser Long userId) {
        double credits = stabilityBalanceService.getUserStabilityCredits(userId);
        return ResponseEntity.ok(new StabilityCreditsResponse(credits));
    }
}
