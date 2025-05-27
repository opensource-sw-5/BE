package com.vata.auth.application;

import com.vata.auth.domain.service.UserService;
import com.vata.auth.dto.SignupRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthFacade {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Transactional // signup 메서드에 @Transactional 적용
    public void signup(SignupRequest signupRequest) {
        userService.save(signupRequest);
    }

    /*
    로그인 처리 메서드
        - request 파라미터 : HTTP 요청 객체, 세션 생성 및 관리하는 데 사용
        - 사용자 조회 : 데이터베이스 -> findByEmail -> 해당 user 객체 생성
        - 비밀번호 확인 : 데이터베이스 pw와 파라미터 pw 비교 -> session 생성 및 정보 저장
     */
    public void login(String email, String password, HttpServletRequest request) {
        try {
            // 1. 인증 토큰 생성 (사용자 입력 정보)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(email, password);

            // 2. AuthenticationManager를 통해 인증 시도
            //    loadUserByUsername 호출 -> 비밀번호 검증 (PasswordEncoder)
            Authentication authentication = authenticationManager.authenticate(authToken);

            // 3. SecurityContextHolder에 인증 정보 저장 (세션에 저장될 내용)
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 4. 세션에 SecurityContext 저장 (Spring Security가 자동으로 세션을 관리)
            //    기존 수동 세션 관리를 이 부분으로 대체
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }
    }

    public void logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
