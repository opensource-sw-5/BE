/*
회원가입 비즈니스 로직
    클라이언트로부터 전달된 회원가입 요청 데이터 검증
    1. 중복된 사용자 정보 있는지 확인
    2. 새로운 사용자 정보 데이터베이스 저장
 */
package com.vata.join.service;

import com.vata.join.domain.User;
import com.vata.join.dto.SignupRequest;
import com.vata.join.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service    // 스프링의 서비스 컴포넌트로 등록
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional // signup 메서드에 @Transactional 적용
    public void signup(SignupRequest signupRequest) {
        // 중복 검사
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 주소입니다.");
        }
        if (userRepository.findByNickname(signupRequest.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = new User();
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        // 비밀번호 암호화하여 User 객체에 저장
        user.setNickname(signupRequest.getNickname());
        user.setEmail(signupRequest.getEmail());

        userRepository.save(user);  //JpaRepository 로부터 상속
    }

}
