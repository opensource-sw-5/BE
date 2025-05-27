package com.vata.auth.controller;

import com.vata.auth.application.UserFacade;
import com.vata.auth.controller.swagger.UserControllerSpec;
import com.vata.auth.domain.entity.User;
import com.vata.auth.dto.UserResponse;
import com.vata.common.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserControllerSpec {
    private final UserFacade userFacade;

    @GetMapping("/info")
    public ResponseEntity<UserResponse> getMyInfo(@LoginUser Long userId) {
        UserResponse response = userFacade.getUserInfo(userId);
        return ResponseEntity.ok(response);
    }
}
