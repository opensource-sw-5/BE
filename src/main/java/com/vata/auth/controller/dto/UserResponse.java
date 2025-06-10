package com.vata.auth.controller.dto;

import com.vata.auth.domain.entity.User;

public record UserResponse(
        String email,
        String name
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getEmail(), user.getName());
    }
}
