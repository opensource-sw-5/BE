/*
RegistrationController
    클라이언트로부터 들어오는 회원가입 요청(signup POST)을 받아들임
    해당 요청을 처리하기 위해 RegistrationService 인터페이스의 구현체를 호출
    응답 생성 :
    1. 유효하지 않은 데이터에 대한 적절한 응답을 생성하는 역할을 수행 (signup 메서드)
    2. 회원가입 처리 결과에 따라 성공 또는 실패 응답을 클라이언트에게 반환
 */
package com.vata.join.controller;

import com.vata.join.service.RegistrationService;
import com.vata.join.dto.SignupRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // http 응답을 나타내는 객체 (상태 코드 + 메시지)
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RESTful API의 컨트롤러 (@Controller와 @ResponseBody를 포함)
@RequestMapping("/service") // 해당 컨트롤러의 모든 API 엔드포인트의 기본 URL -> /service로 설정
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/signup") // /signup 경로로 들어오는 POST 요청을 처리하는 메서드 정의
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        // @Valid : SignupRequest 객체의 유효성 검사를 수행하도록 스프링에게 지시
        // @RequestBody : 요청 본문에 담긴 JSON 데이터 -> SignupRequest 객체로 변환
        try {
            registrationService.signup(signupRequest);
            return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED);
            // 성공 응답 ("회원가입이 완료되었습니다." + HTTP 상태 코드 201 (Created))
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            // 실패 응답 (해당 예외 메시지 + HTTP 상태 코드 400 (Bad Request))
        }
    }

}
