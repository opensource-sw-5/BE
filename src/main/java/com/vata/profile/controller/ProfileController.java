package com.vata.profile.controller;

import com.vata.common.annotation.LoginUser;
import com.vata.common.web.PagingResult;
import com.vata.profile.application.ProfileFacade;
import com.vata.profile.application.ProfilePromptFacade;
import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.ProfileListResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.controller.swagger.ProfileControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController implements ProfileControllerSpec {

    private final ProfilePromptFacade profilePromptFacade;
    private final ProfileFacade profileFacade;

    @PostMapping("/generate")
    @Override
    public Mono<ResponseEntity<?>> generateImage(@LoginUser Long userId,
                                                 @RequestBody UserInputRequest request) {
        return profilePromptFacade.generateProfileImage(userId, request)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .onErrorResume(IllegalArgumentException.class, e ->
                        Mono.just(ResponseEntity.badRequest().body(e.getMessage()))
                );
    }

    @GetMapping("/list")
    @Override
    public ResponseEntity<PagingResult<ProfileListResponse>> getProfiles(
            @LoginUser Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size
    ) {
        PagingResult<ProfileListResponse> result = PagingResult.from(profileFacade.getProfileList(userId, page, size));
        return ResponseEntity.ok(result);
    }
}
