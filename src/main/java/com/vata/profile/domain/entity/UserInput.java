package com.vata.profile.domain.entity;

import com.vata.profile.controller.dto.UserInputRequest;
import com.vata.profile.domain.entity.vo.CharacterType;
import com.vata.profile.domain.entity.vo.Gender;
import com.vata.profile.domain.entity.vo.Mbti;
import com.vata.profile.domain.entity.vo.StyleType;
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

        prompt.append("Create a profile image with the following characteristics: ");
        prompt.append(gender.getPrompt()).append(", ");
        prompt.append(mbti.getPrompt()).append(", ");
        prompt.append("hobby is ").append(hobby).append(", ");
        prompt.append(characterType.getPrompt()).append(", ");

        if (etc != null && !etc.isBlank()) {
            prompt.append(", etc: ").append(etc);
        }

        prompt.append(". Please create a unique and expressive profile image that captures these characteristics.");

        return prompt.toString();
    }
}
