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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service    // 스프링의 서비스 컴포넌트로 등록
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

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

    /*
    이메일로 사용자 조회
        - 데이터베이스에서 해당 이메일을 가진 사용자를 찾음
        - 사용자가 존재하면, 해당 사용자의 이메일, 암호화된 비밀번호, 'USER'라는 기본 권한을 담은
          SpringSecurity의 UserDetails 객체를 생성하여 반환
        - 만약 사용자가 존재하지 않으면, UsernameNotFoundException 예외를 던져서
          SpringSecurity에게 해당 사용자를 찾을 수 없음을 알림
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("USER"))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다: " + email));
    }

    /*
    로그인 처리 메서드
        - request 파라미터 : HTTP 요청 객체, 세션 생성 및 관리하는 데 사용
        - 사용자 조회 : 데이터베이스 -> findByEmail -> 해당 user 객체 생성
        - 비밀번호 확인 : 데이터베이스 pw와 파라미터 pw 비교 -> session 생성 및 정보 저장

     */
    public void login(String email, String password, HttpServletRequest request) {
        // 1. 이메일로 사용자 조회
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 아이디(이메일)입니다.");
        }
        User user = optionalUser.get();

        // 2. 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 비밀번호 일치 -> 세션 생성 및 사용자 정보 저장
        HttpSession session = request.getSession(); // 현재 요청에 대한 세션 가져옴
        session.setAttribute("userId", user.getId()); // 사용자 ID 저장
        session.setAttribute("email", user.getEmail()); // 사용자 이메일 저장
    }
}

