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
        return switch (this) {
            case AVATAR ->
                    "The character type is avatar. Please render this profile picture as a stylized digital human character.";
            case CHARACTER ->
                    "The character type is character. Please transform this profile picture into a cartoon or anime-style character.";
            case ANIMAL ->
                    "The character type is animal. Please represent this profile picture as an anthropomorphic cartoon-style animal character, such as a bear, dog, or fox, in a friendly and expressive pose.";
        };
    }

}
