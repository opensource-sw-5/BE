package com.vata.profile.domain.entity.vo;

public enum Mbti {
    ISTJ("practical and responsible personality"),
    ISFJ("warmhearted and dependable personality"),
    INFJ("gentle and insightful personality"),
    INTJ("strategic and independent personality"),
    ISTP("analytical and action-oriented personality"),
    ISFP("sensitive and artistic personality"),
    INFP("idealistic and introspective personality"),
    INTP("curious and logical personality"),
    ESTP("energetic and bold personality"),
    ESFP("outgoing and playful personality"),
    ENFP("energetic and imaginative personality"),
    ENTP("clever and inventive personality"),
    ESTJ("organized and assertive personality"),
    ESFJ("caring and sociable personality"),
    ENFJ("charismatic and empathetic personality"),
    ENTJ("assertive and strategic leader");

    private final String description;

    Mbti(String description) {
        this.description = description;
    }

    public String getPrompt(){
        return "personality is" + description;
    }
}
