package com.vata.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AccessKeyRequest(
        @NotBlank String value
) {
}
