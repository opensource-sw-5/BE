package com.vata.profile.domain.entity.vo;

public enum StyleType {
    REALISTIC("highly detailed realistic photograph"),
    ILLUSTRATION("digital illustration"),
    PIXEL_ART("8-bit pixel art style");

    private final String stylePrompt;

    StyleType(String stylePrompt) {
        this.stylePrompt = stylePrompt;
    }

    public String getPrompt() {
        return "styleType is" + stylePrompt;
    }
}
