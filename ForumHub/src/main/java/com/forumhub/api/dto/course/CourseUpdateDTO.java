package com.forumhub.api.dto.course;

import jakarta.validation.constraints.NotBlank;

public record CourseUpdateDTO(
        @NotBlank
        String name,
        @NotBlank
        String category
) {
}
