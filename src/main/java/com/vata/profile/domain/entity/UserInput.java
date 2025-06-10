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
        StringBuilder prompt = new StringBuilder();

        // 1. 캐릭터 유형 설명 먼저 (animal은 주체를 바꿔줘야 함)
        String characterDescription = switch (characterType) {
            case ANIMAL -> "An anthropomorphic cartoon-style animal character, such as a bear, dog, or fox. ";
            case AVATAR -> "A stylized digital human character. ";
            case CHARACTER -> "A cartoon or anime-style character. ";
        };

        // 2. 취미 시각화
        Map<String, String> hobbyVisualDescriptions = Map.of(
                "운동", "doing exercise in sporty clothing, in action, sweating slightly",
                "독서", "reading a book calmly with a book in hand",
                "게임", "playing video games with a console controller",
                "음악 감상", "wearing headphones, listening to music with eyes closed",
                "영화 감상", "watching a movie on a screen with popcorn in hand",
                "그림 그리기", "drawing or painting with visible art tools",
                "사진 촬영", "holding a camera and taking photos outdoors"
        );

        // 3. 배경 묘사
        Map<String, String> hobbyBackgrounds = Map.of(
                "운동", "on a sports field or in a gym",
                "독서", "in a cozy room with bookshelves",
                "게임", "in a gaming room with colorful lights",
                "음악 감상", "in a music-themed room",
                "영화 감상", "in a home theater setup",
                "그림 그리기", "in an art studio with sketches on the wall",
                "사진 촬영", "in a scenic park or urban street"
        );

        String visualHobby = hobbyVisualDescriptions.getOrDefault(hobby, "engaging in " + hobby);
        String background = hobbyBackgrounds.getOrDefault(hobby, "in a background that matches the activity");

        // 4. 프롬프트 구성
        prompt.append("Create a profile image of ");

        if (characterType == CharacterType.ANIMAL) {
            prompt.append(characterDescription);
        } else {
            prompt.append("a ").append(gender.name().toLowerCase()).append(" character. ");
            prompt.append(characterDescription);
        }

        prompt.append("The character is ")
                .append(visualHobby).append(", ")
                .append(background).append(". ");

        if (mbti != null) {
            prompt.append("The character’s personality is ").append(mbti.getPrompt()).append(". ");
        }

        if (etc != null && !etc.isBlank()) {
            prompt.append("Additional instructions: ").append(etc).append(". ");
        }

        prompt.append("Ensure that the character’s style and emotion align with the described hobby and personality.");

        return prompt.toString();
    }
}
