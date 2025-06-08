package com.vata.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final int MAX_IN_MEMORY_SIZE_MB = 1;
    private static final int BYTES_PER_MB = 1024 * 1024;

    @Bean
    public WebClient.Builder webClientBuilder() {
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer
                        .defaultCodecs()
                        .maxInMemorySize(MAX_IN_MEMORY_SIZE_MB * BYTES_PER_MB)
                )
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies);
    }
}
