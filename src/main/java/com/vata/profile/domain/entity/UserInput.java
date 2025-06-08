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

        // 취미에 따른 시각적 묘사
        Map<String, String> hobbyVisualDescriptions = Map.of(
                "운동", "doing exercise in sporty clothing, in action, sweating slightly",
                "독서", "reading a book while sitting calmly, book in hands",
                "게임", "playing video games with a controller or headset",
                "음악 감상", "listening to music with large headphones, eyes closed",
                "영화 감상", "watching a movie on a screen, cozy atmosphere",
                "그림 그리기", "drawing with colored pencils or a tablet, sketchpad visible",
                "사진 촬영", "taking a photo with a DSLR camera, camera in hands"
        );

        // 취미에 따른 배경 제안
        Map<String, String> hobbyBackgrounds = Map.of(
                "운동", "in a dynamic sports field or gym background",
                "독서", "in a cozy room with a bookshelf in the background",
                "게임", "with colorful game-themed elements in the background",
                "음악 감상", "in a room with soft lighting and music posters on the wall",
                "영화 감상", "in a home theater or movie night setting",
                "그림 그리기", "with an art studio or sketchbook-filled background",
                "사진 촬영", "in a natural outdoor setting, like a park or street with scenic views"
        );

        String visualHobby = hobbyVisualDescriptions.getOrDefault(hobby, "");
        String characterPrompt = characterType.getPrompt();

        // 프롬프트 시작
        prompt.append("Create a profile image of a ")
                .append(gender.getPrompt()).append(" with a ")
                .append(mbti.getPrompt()).append(". ")
                .append("The character enjoys ").append(hobby).append(". ");

        if (!visualHobby.isBlank()) {
            prompt.append("Visually show the character ").append(visualHobby).append(". ");
        } else {
            prompt.append("Visually show the character doing something related to ").append(hobby).append(". ");
        }

        prompt.append(characterPrompt).append(" ");

        // etc가 비어 있을 경우 hobby 기반 배경 추가
        if (etc != null && !etc.isBlank()) {
            prompt.append("Additional details: ").append(etc).append(". ");
        } else {
            String background = hobbyBackgrounds.getOrDefault(
                    hobby,
                    "in a background that matches the character's mood and style"
            );
            prompt.append("Make the background ").append(background).append(". ");
        }

        prompt.append("Make sure the character's personality and hobby are clearly expressed visually.");

        return prompt.toString();
    }

}
