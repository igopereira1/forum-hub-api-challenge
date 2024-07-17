package com.forumhub.api.dto.topic;

import jakarta.validation.constraints.NotBlank;

public record TopicUpdateDTO(
        @NotBlank
        String title,
        @NotBlank
        String message
) {}
