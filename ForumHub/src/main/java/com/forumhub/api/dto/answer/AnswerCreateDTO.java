package com.forumhub.api.dto.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AnswerCreateDTO(
        @NotBlank
        String message,

        @NotNull
        Long topicId
) {}
