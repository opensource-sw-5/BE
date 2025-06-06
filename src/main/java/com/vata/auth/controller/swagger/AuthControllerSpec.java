package com.vata.auth.controller.swagger;

import com.vata.auth.dto.LoginRequest;
import com.vata.auth.dto.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "Vata Auth", description = "회원가입/로그인")
public interface AuthControllerSpec {

    @Operation(summary = "회원가입", description = "회원가입")
    ResponseEntity<String> signup(
            @Parameter(description = "회원가입 정보")
            SignupRequest signupRequest
    );

    @Operation(summary = "로그인", description = "로그인")
    ResponseEntity<String> login(
            @Parameter(description = "로그인 정보")
            LoginRequest loginRequest,
            HttpServletRequest request
    );

    @Operation(summary = "로그아웃", description = "사용자 세션 정보 삭제")
    ResponseEntity<String> logout(
            HttpServletRequest request
    );

    @Operation(summary = "api key 검증", description = "stability api key 검증")
    ResponseEntity<String> verifyToken(
            @Parameter(description = "stability api key") String apiKey
    );
}
