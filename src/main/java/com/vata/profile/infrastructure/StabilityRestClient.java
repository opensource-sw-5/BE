package com.vata.profile.infrastructure;

import lombok.RequiredArgsConstructor;
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

    private static final String STABILITY_API_URL = "https://api.stability.ai/v2beta/stable-image/generate/core";
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

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(STABILITY_API_URL, HttpMethod.POST, entity, byte[].class);
    }
}
