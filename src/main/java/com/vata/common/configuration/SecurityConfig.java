package com.vata.common.configuration;

import com.vata.auth.service.UserDetailService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity  // 스프링 시큐리티 기능 활성화
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailService userDetailService; // UserDetailService 주입

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

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailService) // 우리가 구현한 UserDetailsService (UserDetailService) 연결
                .passwordEncoder(passwordEncoder()); // 사용할 PasswordEncoder 연결

        return authenticationManagerBuilder.build();
    }

    //  HTTP 요청에 대한 보안 설정을 정의
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())// 개발 편의상 CSRF 비활성화 (실제 서비스에서는 활성화 권장)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PERMIT_ALL_PATTERNS).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080", "http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
