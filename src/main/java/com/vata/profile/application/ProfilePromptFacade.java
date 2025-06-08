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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfilePromptFacade {
    private final ProfileService profileService;
    private final StabilityImageService stabilityImageService;
    private final AccessKeyService accessKeyService;
    private final MinioService minioService;

    private static final String CONTENT_TYPE = "image/jpeg";

    public Mono<ImageGenerateResponse> generateProfileImage(Long userId, UserInputRequest request) {
        String prompt = generatePrompt(request);

        log.info("🎯 Generated Prompt: {}", prompt);

        String apiKey = accessKeyService.getValue(userId);
        String filePath = String.format("profiles/%d/%s.jpg", userId, UUID.randomUUID());

        return stabilityImageService.generateImage(prompt, apiKey, request.styleType())
                .flatMap(imageBytes -> {
                    // MinIO 저장도 비동기 지원이 되면 here → uploadMono(...)
                    String imageUrl = minioService.uploadFile( // 동기 방식이면 이 부분만 block() 가능
                            filePath,
                            new ByteArrayInputStream(imageBytes),
                            imageBytes.length,
                            CONTENT_TYPE
                    );
                    Profile profile = profileService.save(userId, imageUrl); // 저장 처리
                    ImageGenerateResponse response = new ImageGenerateResponse(
                            profile.getId(), imageUrl, profile.getCreatedAt(), CONTENT_TYPE
                    );
                    return Mono.just(response);
                });
    }

    private String generatePrompt(UserInputRequest request) {
        UserInput input = UserInput.of(request);
        return input.generatePrompt();
    }
}
