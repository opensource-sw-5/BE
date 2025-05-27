/*
회원가입 비즈니스 로직
    클라이언트로부터 전달된 회원가입 요청 데이터 검증
    1. 중복된 사용자 정보 있는지 확인
    2. 새로운 사용자 정보 데이터베이스 저장
 */
package com.vata.auth.domain.service;

import com.vata.auth.domain.entity.User;
import com.vata.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service    // 스프링의 서비스 컴포넌트로 등록
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    /*
    이메일로 사용자 조회
        - 데이터베이스에서 해당 이메일을 가진 사용자를 찾음
        - 사용자가 존재하면, 해당 사용자의 이메일, 암호화된 비밀번호, 'USER'라는 기본 권한을 담은
          SpringSecurity의 UserDetails 객체를 생성하여 반환
        - 만약 사용자가 존재하지 않으면, UsernameNotFoundException 예외를 던져서
          SpringSecurity에게 해당 사용자를 찾을 수 없음을 알림
    Spring Security의 로그아웃 처리
        - 세션 기반 인증의 로그아웃은 주로 SpringSecurity 프레임워크가 담당
        - 클라이언트가 설정된 로그아웃 url로 요청을 보냄 (SecurityConfig에서 설정)
        - Spring Security의 LogoutFilter가 해당 요청 가로챔
        - LogoutFilter가 설정된 대로 다음 작업 수행 (SecurityConfig에서 설정)
     */
    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일의 사용자를 찾을 수 없습니다: " + email));
    }
}
