package com.vata.profile.application;

import com.vata.auth.domain.service.AccessKeyService;
import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.domain.entity.Profile;
import com.vata.profile.domain.entity.UserInput;
import com.vata.profile.domain.service.ProfileService;
import com.vata.profile.domain.service.StabilityImageService;
import com.vata.profile.infrastructure.MinioService;
import java.io.ByteArrayInputStream;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfilePromptFacade {
    private final ProfileService profileService;
    private final StabilityImageService stabilityImageService;
    private final AccessKeyService accessKeyService;
    private final MinioService minioService;

    private static final String CONTENT_TYPE = "image/jpeg";

    public ImageGenerateResponse generateProfileImage(Long userId, UserInputRequest request) {
        String prompt = generatePrompt(request);
        String apiKey = accessKeyService.getValue(userId);
        byte[] imageBytes = stabilityImageService.generateImage(prompt, apiKey, request.styleType());

        String filePath = String.format("profiles/%d/%s.jpg", userId, UUID.randomUUID());
        String imageUrl = minioService.uploadFile(
                filePath,
                new ByteArrayInputStream(imageBytes),
                imageBytes.length,
                CONTENT_TYPE
        );

        Profile profile = profileService.save(userId, imageUrl);

        return new ImageGenerateResponse(profile.getId(), imageUrl, profile.getCreatedAt(), CONTENT_TYPE);
    }

    private String generatePrompt(UserInputRequest request) {
        UserInput input = UserInput.of(request);
        return input.generatePrompt();
    }
}
