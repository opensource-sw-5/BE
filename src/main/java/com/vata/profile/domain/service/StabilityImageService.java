package com.vata.profile.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vata.profile.domain.entity.vo.NegativePrompt;
import com.vata.profile.domain.entity.vo.StyleType;
import com.vata.profile.infrastructure.StabilityRestClient;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StabilityImageService {
    private static final long MAX_SEED = 4294967294L;

    private final StabilityRestClient stabilityRestClient;

    public byte[] generateImage(String prompt, String apiKey, StyleType styleType) {
        long seed = generateSeed();

        ResponseEntity<byte[]> response = stabilityRestClient.generateImage(
                apiKey,
                prompt,
                NegativePrompt.getNegativePrompt(),
                seed,
                styleType.getStylePreset()
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            String errorMessage = "알 수 없는 오류";
            try {
                byte[] body = response.getBody();
                if (body != null && body.length > 0) {
                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> errorMap = mapper.readValue(body, Map.class);
                    errorMessage = (String) errorMap.getOrDefault("message", errorMessage);
                }
            } catch (Exception e) {
                errorMessage = "에러 메시지 파싱 실패";
            }

            throw new IllegalArgumentException(
                    "이미지 생성 실패: " + errorMessage + " (status=" + response.getStatusCode() + ")");
        }
    }


    private long generateSeed() {
        return (long) (Math.random() * MAX_SEED);
    }
}
