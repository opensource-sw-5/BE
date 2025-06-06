package com.vata.profile.domain.entity;

import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.domain.entity.vo.CharacterType;
import com.vata.profile.domain.entity.vo.Gender;
import com.vata.profile.domain.entity.vo.Mbti;
import com.vata.profile.domain.entity.vo.StyleType;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class UserInput {

    private final Gender gender;
    private final Mbti mbti;
    private final String hobby;
    private final CharacterType characterType;
    private final StyleType styleType;
    private final String etc;

    public static UserInput of(UserInputRequest request) {
        return UserInput.builder()
                .gender(request.gender())
                .mbti(request.mbti())
                .hobby(request.hobby())
                .characterType(request.characterType())
                .styleType(request.styleType())
                .etc(request.etc())
                .build();
    }

    public String generatePrompt() {
        Map<String, String> hobbyTranslations = Map.of(
                "운동", "working out",
                "독서", "reading",
                "게임", "playing games",
                "음악 감상", "listening to music",
                "영화 감상", "watching movies",
                "그림 그리기", "drawing",
                "사진 촬영", "taking photos"
        );
        String translatedHobby = hobbyTranslations.getOrDefault(hobby, hobby);

        StringBuilder prompt = new StringBuilder();

        prompt.append("Create a profile image with the following characteristics: ");
        prompt.append(gender.getPrompt()).append(", ");
        prompt.append(mbti.getPrompt()).append(", ");
        prompt.append("hobby is ").append(translatedHobby).append(", ");
        prompt.append(characterType.getPrompt()).append(", ");

        if (etc != null && !etc.isBlank()) {
            prompt.append("Additional details: ").append(etc).append(". ");
        }

        prompt.append("If no background is specified, use a background that matches the overall mood and style. ");
        prompt.append("Please create a unique and expressive profile image that captures these characteristics.");

        return prompt.toString();
    }
}
