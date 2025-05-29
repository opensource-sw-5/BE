package com.vata.auth.domain.service;

import com.vata.auth.domain.entity.AccessKey;
import com.vata.auth.domain.entity.User;
import com.vata.auth.domain.repository.AccessKeyRepository;
import com.vata.auth.domain.repository.UserRepository;
import com.vata.auth.dto.SignupRequest;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AccessKeyRepository accessKeyRepository; // AccessKeyRepository 주입
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User save(SignupRequest signupRequest) {
        validateEmail(signupRequest.email());

        // 1. User 엔티티 생성 및 저장
        User user = User.of(signupRequest, passwordEncoder);
        User savedUser = userRepository.save(user); // 저장된 User 객체를 받아 ID를 사용

        // 2. AccessKey 엔티티 생성 및 저장 (Stability AI Access Token 저장)
        // User 객체가 DB에 저장되어야 ID를 얻을 수 있으므로, savedUser.getId()를 사용
        AccessKey accessKey = AccessKey.of(savedUser.getId(), signupRequest.stabilityApiAccessToken());
        accessKeyRepository.save(accessKey); // AccessKey 저장

        return savedUser;
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    private void validateEmail(String email){
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일 주소입니다.");
        }
    }
}
