package com.vata.profile.domain.entity.vo;

import lombok.Getter;

@Getter
public enum NegativePrompt {

    BLUR("blurry"),
    LOW_RES("low resolution"),
    DEFORMED("deformed"),
    EXTRA_LIMBS("extra limbs"),
    LOW_QUALITY("poorly drawn"),
    TEXT("text"),
    WATERMARK("watermark");

    private final String keyword;

    NegativePrompt(String keyword) {
        this.keyword = keyword;
    }
}
