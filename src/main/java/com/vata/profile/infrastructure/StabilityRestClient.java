package com.vata.profile.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class StabilityRestClient {

    private final WebClient.Builder webClientBuilder;

    @Value("${stability.api.base-url}")
    private String baseUrl;

    @Value("${stability.api.image-generate-path}")
    private String imageGeneratePath;

    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final String OUTPUT_FORMAT = "jpeg";

    public Mono<byte[]> generateImage(String apiKey, String prompt, String negativePrompt, long seed, String stylePreset) {
        String apiUrl = baseUrl + imageGeneratePath;

        Map<String, Object> formData = new HashMap<>();
        formData.put("prompt", prompt);
        formData.put("negative_prompt", negativePrompt);
        formData.put("seed", seed);
        formData.put("style_preset", stylePreset);
        formData.put("output_format", OUTPUT_FORMAT);

        return webClientBuilder.build()
                .post()
                .uri(apiUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.IMAGE_JPEG)
                .header("Authorization", AUTH_HEADER_PREFIX + apiKey)
                .body(BodyInserters.fromMultipartData(toMultiValueMap(formData)))
                .retrieve()
                .bodyToMono(byte[].class)
                .onErrorResume(e -> {
                    return Mono.error(new IllegalArgumentException("Stability API 호출 실패: " + e.getMessage(), e));
                });
    }

    private org.springframework.util.MultiValueMap<String, Object> toMultiValueMap(Map<String, Object> map) {
        org.springframework.util.LinkedMultiValueMap<String, Object> multiValueMap = new org.springframework.util.LinkedMultiValueMap<>();
        map.forEach(multiValueMap::add);
        return multiValueMap;
    }
}
