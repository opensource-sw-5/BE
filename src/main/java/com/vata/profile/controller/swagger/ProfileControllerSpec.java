package com.vata.profile.controller.swagger;

import com.vata.common.annotation.LoginUser;
import com.vata.profile.controller.dto.ImageGenerateResponse;
import com.vata.profile.controller.dto.StabilityCreditsResponse;
import com.vata.profile.controller.dto.UserInputRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Profile Prompt API", description = "AI 프로필 생성 API")
public interface ProfileControllerSpec {

    @Operation(
            summary = "프로필 생성",
            description = "사용자 입력을 기반으로 AI 프로필을 생성합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(name = "프로필 생성 요청", value = GENERATE_PROFILE_PROMPT_REQUEST)
                    )
            )
    )
    ResponseEntity<ImageGenerateResponse> generateImage(@LoginUser Long userId, UserInputRequest request);

    @Operation(
            summary = "사용자 Stability AI 크레딧 잔액 조회", // API 요약
            description = "현재 로그인된 사용자의 Stability AI API 크레딧 잔액을 조회합니다." // API 상세 설명
    )
    @ApiResponse(responseCode = "200", description = "크레딧 잔액 조회 성공",
            content = @Content(schema = @Schema(implementation = StabilityCreditsResponse.class))) // 200 응답 시 반환될 DTO 명시
    @ApiResponse(responseCode = "401", description = "인증 실패 (유효하지 않은 Access Token 또는 로그인 필요)", content = @Content) // 401 응답 명시
    @ApiResponse(responseCode = "500", description = "서버 오류 또는 크레딧 조회 실패", content = @Content) // 500 응답 명시
    @GetMapping("/credits") // 실제 엔드포인트 경로를 인터페이스 메서드에 명시
    ResponseEntity<StabilityCreditsResponse> getUserCredits(
            @Parameter(hidden = true) @LoginUser Long userId // @LoginUser 파라미터는 Swagger 문서에서 숨김
    );

    String GENERATE_PROFILE_PROMPT_REQUEST = """
            {
              "gender": "FEMALE",
              "mbti": "ENTJ",
              "hobby": "playing the guitar",
              "characterType": "AVATAR",
              "styleType": "MODEL_3D",
              "etc": "Make the background feel like a warm and cozy library."
            }
            """;
}
