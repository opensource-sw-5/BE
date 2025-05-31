package com.vata.profile.controller.dto;

public record ProfileListResponse(Long profileId, String profileImageUrl, java.time.LocalDateTime createdAt) {
}
