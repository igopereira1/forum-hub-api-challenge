package com.forumhub.api.dto.answer;

import com.forumhub.api.model.Answer;

public record AnswerDetailsDTO(
        Long id,
        String message,
        Long topicId,
        Long authorId,
        boolean solution
) {
    public AnswerDetailsDTO(Answer answer) {
        this(
                answer.getId(),
                answer.getMessage(),
                answer.getTopic().getId(),
                answer.getAuthor().getId(),
                answer.isSolution()
        );
    }
}