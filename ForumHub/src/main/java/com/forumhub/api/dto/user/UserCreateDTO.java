package com.forumhub.api.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserCreateDTO(
        @NotBlank(message = "Login is required")
        String login,
        @NotBlank(message = "Password is required")
        String password
) {}