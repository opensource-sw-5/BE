package com.vata.profile.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class StabilityRestClient {

    private final RestTemplate restTemplate;

    @Value("${stability.api.base-url}")
    private String baseUrl;

    @Value("${stability.api.image-generate-path}")
    private String imageGeneratePath;
    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final String ACCEPT_HEADER_VALUE = "image/*";
    private static final String OUTPUT_FORMAT = "jpeg";

    public ResponseEntity<byte[]> generateImage(String apiKey, String prompt, String negativePrompt, long seed, String stylePreset) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Authorization", AUTH_HEADER_PREFIX + apiKey);
        headers.set("Accept", ACCEPT_HEADER_VALUE);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("prompt", prompt);
        body.add("negative_prompt", negativePrompt);
        body.add("seed", seed);
        body.add("style_preset", stylePreset);
        body.add("output_format", OUTPUT_FORMAT);

        String apiUrl = baseUrl + imageGeneratePath;

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(apiUrl, HttpMethod.POST, entity, byte[].class);
    }
}
