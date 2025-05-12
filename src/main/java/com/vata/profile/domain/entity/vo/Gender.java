package com.vata.profile.domain.entity.vo;

public enum Gender {
    MALE("male"),
    FEMALE("female"),
    NONE("unspecified");

    private final String promptKeyword;

    Gender(String promptKeyword) {
        this.promptKeyword = promptKeyword;
    }

    public String getPrompt() {
        return "gender is " + promptKeyword;
    }
}
