package com.vata.profile.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInput {

    private final Gender gender;
    private final MBTI mbti;
    private final String hobby;
    private final CharacterType characterType;
    private final StyleType styleType;
    private final String etc;
}
