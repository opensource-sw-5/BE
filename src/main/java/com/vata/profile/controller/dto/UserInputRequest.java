package com.vata.profile.controller.dto;

import com.vata.profile.domain.entity.vo.CharacterType;
import com.vata.profile.domain.entity.vo.Gender;
import com.vata.profile.domain.entity.vo.Mbti;
import com.vata.profile.domain.entity.vo.StyleType;

public record UserInputRequest(
        Gender gender,
        Mbti mbti,
        String hobby,
        CharacterType characterType,
        StyleType styleType,
        String etc
) {}
