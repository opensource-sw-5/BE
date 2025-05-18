package com.vata.profile.domain.entity.vo;

import lombok.Getter;

public enum StyleType {
    MODEL_3D("3d-model"),
    ANALOG_FILM("analog-film"),
    ANIME("anime"),
    CINEMATIC("cinematic"),
    COMIC_BOOK("comic-book"),
    DIGITAL_ART("digital-art"),
    ENHANCE("enhance"),
    FANTASY_ART("fantasy-art"),
    ISOMETRIC("isometric"),
    LINE_ART("line-art"),
    LOW_POLY("low-poly"),
    MODELING_COMPOUND("modeling-compound"),
    NEON_PUNK("neon-punk"),
    ORIGAMI("origami"),
    PHOTOGRAPHIC("photographic"),
    PIXEL_ART("pixel-art"),
    TILE_TEXTURE("tile-texture");

    private final String stylePreset;

    StyleType(String stylePreset) {
        this.stylePreset = stylePreset;
    }

    public String getStylePreset() {
        return this.stylePreset;
    }
}
