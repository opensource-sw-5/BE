package com.vata.profile.controller.dto;

import java.time.LocalDateTime;

public record ImageGenerateResponse(String imageUrl, LocalDateTime createdAt, String contentType) {
}
