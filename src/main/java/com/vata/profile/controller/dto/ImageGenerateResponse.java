package com.vata.profile.controller.dto;

import java.time.LocalDateTime;

public record ImageGenerateResponse(Long id, String imageUrl, LocalDateTime createdAt, String contentType) {
}
