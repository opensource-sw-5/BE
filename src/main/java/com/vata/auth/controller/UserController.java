package com.vata.auth.controller;

import com.vata.auth.controller.swagger.UserControllerSpec;
import com.vata.auth.domain.entity.User;
import com.vata.auth.dto.UserResponse;
import com.vata.common.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController implements UserControllerSpec {

    @GetMapping("/info")
    public UserResponse getMyInfo(@LoginUser User user) {
        return UserResponse.from(user);
    }
}
