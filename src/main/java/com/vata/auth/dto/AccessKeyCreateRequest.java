package com.vata.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AccessKeyCreateRequest(
        @NotBlank String value
) {
}
