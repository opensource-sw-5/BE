package com.vata.profile.domain.service;

import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.domain.entity.vo.NegativePrompt;
import com.vata.profile.domain.entity.vo.StyleType;
import com.vata.profile.infrastructure.StabilityRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StabilityImageService {
    private static final long MAX_SEED = 4294967294L;
    private static final String CONTENT_TYPE = "image/jpeg";

    private final StabilityRestClient stabilityRestClient;


    public ImageGenerateResponse generateImage(Long userId, String prompt, StyleType styleType) {
        // String apiKey = userRepository.findApiKeyByUserId(userId)
        //        .orElseThrow(() -> new IllegalArgumentException("API 키가 존재하지 않습니다. userId=" + userId));
        String apiKey = "";
        long seed = generateSeed();

        ResponseEntity<byte[]> response = stabilityRestClient.generateImage(
                apiKey,
                prompt,
                NegativePrompt.getNegativePrompt(),
                seed,
                styleType.getStylePreset()
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ImageGenerateResponse(response.getBody(), CONTENT_TYPE);
        }
        throw new RuntimeException("API 오류");
    }

    private long generateSeed() {
        return (long) (Math.random() * MAX_SEED);
    }
}
