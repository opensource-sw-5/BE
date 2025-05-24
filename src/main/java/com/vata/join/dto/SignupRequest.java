/*
데이터 전송 (Data Transfer Object - DTO)
    1. 클라이언트가 입력한 회원가입에 필요한 정보 (아이디, 비밀번호, 닉네임, 이메일 등)를 담아서
    서버 측 컨트롤러 (RegistrationController)에 전달하는 역할
    2. 클라이언트가 전송한 요청 데이터를 편리하게 객체 형태로 받을 수 있도록 해줌 (signup 입장)
    3. 데이터 유효성 검사
 */
package com.vata.join.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SignupRequest (
        @NotBlank(message = "이메일(아이디)을 입력해주세요.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.") // 아이디가 이메일이라면 여기에 @Email을 붙이는 것이 자연스럽습니다.
        String email, // User 엔티티의 email과 동일하게 사용하며, 이것이 아이디 역할을 합니다.

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "이름을 입력해주세요.") // 엔티티 필드명 'name'과 일치
        String name
) {}
