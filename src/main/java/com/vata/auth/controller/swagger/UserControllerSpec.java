package com.vata.auth.controller.swagger;

import com.vata.auth.dto.UserResponse;
import com.vata.common.annotation.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "User API", description = "회원 정보 API")
public interface UserControllerSpec {

    @Operation(summary = "내 정보 조회", description = "현재 로그인한 사용자의 정보를 반환합니다.")
    ResponseEntity<UserResponse> getMyInfo(@LoginUser Long userId);
}
