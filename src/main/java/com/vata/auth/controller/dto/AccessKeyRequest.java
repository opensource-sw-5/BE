package com.vata.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AccessKeyRequest(
        @NotBlank String accessToken
) {
}
