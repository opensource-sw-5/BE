package com.vata.join;

import com.vata.join.domain.User;
import com.vata.join.dto.SignupRequest;
import com.vata.join.repository.UserRepository;
import com.vata.join.service.RegistrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void signup_성공() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");
        signupRequest.setNickname("테스터");
        signupRequest.setEmail("test@example.com");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByNickname("테스터")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(new User()); // 실제 저장 결과는 중요하지 않음

        // When
        assertDoesNotThrow(() -> registrationService.signup(signupRequest));

        // Then
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).findByNickname("테스터");
        verify(userRepository, times(1)).findByEmail("test@example.com");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void signup_아이디_중복() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("existinguser");
        signupRequest.setPassword("password123");
        signupRequest.setNickname("테스터");
        signupRequest.setEmail("test@example.com");

        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(new User()));

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> registrationService.signup(signupRequest));

        // Then
        assertEquals("이미 사용 중인 아이디입니다.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("existinguser");
        verify(userRepository, never()).findByNickname(anyString());
        verify(userRepository, never()).findByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void signup_닉네임_중복() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");
        signupRequest.setNickname("existingnick");
        signupRequest.setEmail("test@example.com");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByNickname("existingnick")).thenReturn(Optional.of(new User()));

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> registrationService.signup(signupRequest));

        // Then
        assertEquals("이미 사용 중인 닉네임입니다.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).findByNickname("existingnick");
        verify(userRepository, never()).findByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void signup_이메일_중복() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser");
        signupRequest.setPassword("password123");
        signupRequest.setNickname("테스터");
        signupRequest.setEmail("existing@example.com");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(userRepository.findByNickname("테스터")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        // When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> registrationService.signup(signupRequest));

        // Then
        assertEquals("이미 사용 중인 이메일 주소입니다.", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("testuser");
        verify(userRepository, times(1)).findByNickname("테스터");
        verify(userRepository, times(1)).findByEmail("existing@example.com");
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}