package com.forumhub.api.dto.answer;

import jakarta.validation.constraints.NotBlank;

public record AnswerUpdateDTO(
        String message,
        boolean solution
) {
}
