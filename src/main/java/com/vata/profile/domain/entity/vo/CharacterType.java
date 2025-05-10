package com.vata.profile.domain.entity.vo;

public enum CharacterType {
    AVATAR("avatar"),
    CHARACTER("character"),
    ANIMAL("animal");

    private final String promptWord;

    CharacterType(String promptWord) {
        this.promptWord = promptWord;
    }

    public String getPrompt() {
        return "character type is " + promptWord;
    }
}
