package com.forumhub.api.dto.answer;

public record AnswerUpdateDTO(
        String message,
        boolean solution
) {}