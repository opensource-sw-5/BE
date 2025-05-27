package com.vata.auth.application;

import com.vata.auth.domain.entity.User;
import com.vata.auth.domain.service.UserService;
import com.vata.auth.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;

    public UserResponse getUserInfo(Long userId) {
        User user = userService.getById(userId);
        return UserResponse.from(user);
    }
}
