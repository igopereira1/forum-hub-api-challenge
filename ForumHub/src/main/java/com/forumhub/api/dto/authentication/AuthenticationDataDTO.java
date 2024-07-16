package com.forumhub.api.dto.authentication;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDataDTO(
        @NotNull
        String login,
        @NotNull
        String password
) {}
