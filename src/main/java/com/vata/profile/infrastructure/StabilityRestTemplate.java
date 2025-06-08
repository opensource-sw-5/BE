package com.vata.profile.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class StabilityRestTemplate {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${stability.api.base-url}")
    private String baseUrl;

    @Value("${stability.api.balance-path}")
    private String balancePath;

    private static final String AUTH_HEADER_PREFIX = "Bearer ";

    /*
    Stability AI 계정 잔액을 조회하는 메서드
    GET /v1/user/balance 엔드포인트를 호출하는 메서드를 추가 */
    public double getBalanceCredits(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", AUTH_HEADER_PREFIX + apiKey);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE); // 응답은 JSON으로 받음

        String apiUrl = baseUrl + balancePath;

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(response.getBody());

                return rootNode.path("credits").asDouble(); // JSON 응답에서 'credits' 필드를 추출
            } else {
                String errorMessage =
                        response.getBody() != null ? response.getBody() : "Unknown error from Stability AI";
                throw new IllegalStateException("Failed to retrieve Stability AI credits: " + errorMessage + " (status="
                        + response.getStatusCode() + ")");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error while fetching Stability AI credits: " + e.getMessage(), e);
        }
    }
}
