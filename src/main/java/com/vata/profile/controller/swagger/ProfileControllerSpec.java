package com.vata.profile.controller.swagger;

import com.vata.profile.controller.dto.UserInputRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<byte[]> generateImage(UserInputRequest request);

    String GENERATE_PROFILE_PROMPT_REQUEST = """
            {
              "gender": "FEMALE",
              "mbti": "INFP",
              "hobby": "reading",
              "characterType": "CHARACTER",
              "styleType": "ILLUSTRATION",
              "etc": "Make the background feel like a warm and cozy library."
            }
            """;
}
