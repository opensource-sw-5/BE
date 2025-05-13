package com.vata.profile.domain.service;

import com.vata.profile.domain.entity.vo.NegativePrompt;
import com.vata.profile.domain.entity.vo.StyleType;
import com.vata.profile.infrastructure.StabilityImageFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StabilityImageService {

    private final StabilityImageFeignClient stabilityImageFeignClient;

    public byte[] generateImage(String userId, String prompt, StyleType styleType) {
//        String apiKey = userRepository.findApiKeyByUserId(userId)
//                .orElseThrow(() -> new IllegalArgumentException("API 키가 존재하지 않습니다. userId=" + userId));
        String apiKey = "sdfsdf";

        try {
            Resource dummyFile = createDummyFile();
            String negativePrompt = NegativePrompt.getNegativePrompt();
            long seed = generateRandomSeed();
            String stylePreset = styleType.getStylePreset();

            ResponseEntity<byte[]> response = stabilityImageFeignClient.generateImage(
                    "Bearer " + apiKey,
                    "image/*",
                    dummyFile,
                    prompt,
                    negativePrompt,
                    seed,
                    stylePreset,
                    "jpg"
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Stability API 호출 실패 - 상태코드: {}, 사용자: {}", response.getStatusCode(), userId);
                throw new RuntimeException("Stability API 응답 오류: " + response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Stability 이미지 생성 중 예외 발생 - 사용자: {}, 메시지: {}", userId, e.getMessage(), e);
            throw new RuntimeException("이미지 생성 중 오류가 발생했습니다", e);
        }
    }

    private long generateRandomSeed() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }

    private Resource createDummyFile() {
        return new ByteArrayResource("".getBytes()) {
            @Override
            public String getFilename() {
                return "none";
            }
        };
    }
}
