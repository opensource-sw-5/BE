/*
RegistrationController
    클라이언트로부터 들어오는 회원가입 요청(signup POST)을 받아들임
    해당 요청을 처리하기 위해 RegistrationService 인터페이스의 구현체를 호출
    응답 생성 :
    1. 유효하지 않은 데이터에 대한 적절한 응답을 생성하는 역할을 수행 (signup 메서드)
    2. 회원가입 처리 결과에 따라 성공 또는 실패 응답을 클라이언트에게 반환
 */
package com.vata.auth.controller;

import com.vata.auth.application.AuthFacade;
import com.vata.auth.controller.swagger.AuthControllerSpec;
import com.vata.auth.dto.LoginRequest;
import com.vata.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RESTful API의 컨트롤러 (@Controller와 @ResponseBody를 포함)
@RequestMapping("/api/auth") // 해당 컨트롤러의 모든 API 엔드포인트의 기본 URL -> /api/auth로 설정
@RequiredArgsConstructor
public class AuthController implements AuthControllerSpec {

    private final AuthFacade authFacade;

    @PostMapping("/signup") // /signup 경로로 들어오는 POST 요청을 처리하는 메서드 정의
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        // @Valid : SignupRequest 객체의 유효성 검사를 수행하도록 스프링에게 지시
        // @RequestBody : 요청 본문에 담긴 JSON 데이터 -> SignupRequest 객체로 변환
        try {
            authFacade.signup(signupRequest);
            return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED);
            // 성공 응답 ("회원가입이 완료되었습니다." + HTTP 상태 코드 201 (Created))
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            // 실패 응답 (해당 예외 메시지 + HTTP 상태 코드 400 (Bad Request))
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            authFacade.login(loginRequest.email(), loginRequest.password(), request);
            return ResponseEntity.ok("로그인 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        try {
            authFacade.logout(request);
            return ResponseEntity.ok("로그아웃 되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/token/verify")
    @Override
    public ResponseEntity<String> verifyToken(String apiKey){
        try{
            double credits = authFacade.getCredits(apiKey);
            return ResponseEntity.ok("인증된 토큰입니다.");
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
