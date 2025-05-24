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

// 해당 클래스의 필드에 대한 Getter, Setter, toString(), equals(), hashCode(), RequiredArgsConstructor를 자동으로 생성
public record SignupRequest (

    // NotBlank : 해당 필드가 비어있거나 공백만으로 이루어질 수 없음 -> 유효성 검사
    @NotBlank(message = "아이디를 입력해주세요.")
    String username,

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")  // 문자열 필드 최소길이 지정
    String password,

    @NotBlank(message = "닉네임을 입력해주세요.")
    String nickname,

    @Email(message = "유효한 이메일 주소를 입력해주세요.") // 해당 필드가 유효한 이메일 형식이어야 함
    String email
) {}
