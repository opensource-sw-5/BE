package com.vata.auth.application;

import com.vata.auth.domain.entity.User;
import com.vata.auth.domain.service.AccessKeyService;
import com.vata.auth.domain.service.UserService;
import com.vata.auth.controller.dto.AccessKeyRequest;
import com.vata.auth.controller.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final AccessKeyService accessKeyService;

    public UserResponse getUserInfo(Long userId) {
        User user = userService.getById(userId);
        return UserResponse.from(user);
    }

    public void saveAccessKey(Long userId, AccessKeyRequest request) {
        accessKeyService.save(userId, request.accessToken());
    }
}
