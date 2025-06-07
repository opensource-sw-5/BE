package com.vata.auth.controller;

import com.vata.auth.application.UserFacade;
import com.vata.auth.controller.swagger.UserControllerSpec;
import com.vata.auth.dto.AccessKeyRequest;
import com.vata.auth.dto.UserResponse;
import com.vata.common.annotation.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/access-key")
    public ResponseEntity<String> saveAccessKey(
            @LoginUser Long userId,
            @Valid @RequestBody AccessKeyRequest request
    ) {
        userFacade.saveAccessKey(userId, request);
        return ResponseEntity.ok("AccessKey 등록이 완료되었습니다.");
    }
}
