package com.forumhub.api.dto.authentication;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDataDTO(
        @NotNull(message = "Login is required")
        String login,
        @NotNull(message = "Password is required")
        String password
) {}