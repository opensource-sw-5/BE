package com.vata.profile.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StabilityWebClient {
    private final WebClient.Builder webClientBuilder;


    @Value("${stability.api.base-url}")
    private String baseUrl;

    @Value("${stability.api.image-generate-path}")
    private String imageGeneratePath;

    private static final String AUTH_HEADER_PREFIX = "Bearer ";
    private static final String OUTPUT_FORMAT = "jpeg";

    public Mono<byte[]> generateImage(String apiKey, String prompt, String negativePrompt, long seed,
                                      String stylePreset) {
        String apiUrl = baseUrl + imageGeneratePath;

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("prompt", prompt).header("Content-Type", "text/plain");
        builder.part("negative_prompt", negativePrompt).header("Content-Type", "text/plain");
        builder.part("seed", String.valueOf(seed)).header("Content-Type", "text/plain");
        builder.part("style_preset", stylePreset).header("Content-Type", "text/plain");
        builder.part("output_format", OUTPUT_FORMAT).header("Content-Type", "text/plain");

        return webClientBuilder.build()
                .post()
                .uri(apiUrl)
                .header("Authorization", AUTH_HEADER_PREFIX + apiKey)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.parseMediaType("image/*"))
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            System.err.println("Stability API 에러 바디: " + errorBody);
                            return Mono.error(new IllegalArgumentException(
                                    "Stability API 호출 실패: " + errorBody + " (status=" + clientResponse.statusCode()
                                            + ")"
                            ));
                        })
                )
                .bodyToMono(byte[].class);
    }
}
