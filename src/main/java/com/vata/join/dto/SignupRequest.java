package com.vata.join.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
// 해당 클래스의 필드에 대한 Getter, Setter, toString(), equals(), hashCode(), RequiredArgsConstructor를 자동으로 생성
public class SignupRequest {

    // NotBlanck : 해당 필드가 비어있거나 공백만으로 이루어질 수 없음 -> 유효성 검사
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")  // 문자열 필드 최소길이 지정
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    @Email(message = "유효한 이메일 주소를 입력해주세요.") // 해당 필드가 유효한 이메일 형식이어야 함
    private String email;
}
