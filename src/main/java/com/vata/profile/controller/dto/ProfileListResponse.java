package com.vata.profile.controller.dto;

import java.time.LocalDate;

public record ProfileListResponse(Long profileId, String profileImageUrl, LocalDate createdAt) {
}
