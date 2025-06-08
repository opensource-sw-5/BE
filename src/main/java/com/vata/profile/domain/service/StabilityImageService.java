package com.vata.profile.domain.service;

import com.vata.profile.domain.entity.vo.NegativePrompt;
import com.vata.profile.domain.entity.vo.StyleType;
import com.vata.profile.infrastructure.StabilityWebClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class StabilityImageService {
    private static final long MAX_SEED = 4294967294L;

    private final StabilityWebClient stabilityWebClient;

    public Mono<byte[]> generateImage(String prompt, String apiKey, StyleType styleType) {
        long seed = generateSeed();

        return stabilityWebClient.generateImage(
                        apiKey,
                        prompt,
                        NegativePrompt.getNegativePrompt(),
                        seed,
                        styleType.getStylePreset()
                )
                .onErrorMap(e -> new IllegalArgumentException("이미지 생성 실패: " + e.getMessage(), e));
    }

    private long generateSeed() {
        return (long) (Math.random() * MAX_SEED);
    }
}
