package com.vata.join.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity  // 스프링 시큐리티 기능 활성화
public class SecurityConfig {

    private static final String[] PERMIT_ALL_PATTERNS = {
            "/api/auth/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/docs/**"
    };

    @Bean
    //  BCryptPasswordEncoder 빈을 생성하여 스프링 컨테이너에 등록
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //  HTTP 요청에 대한 보안 설정을 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())// 개발 편의상 CSRF 비활성화 (실제 서비스에서는 활성화 권장)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ALL_PATTERNS).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

}
