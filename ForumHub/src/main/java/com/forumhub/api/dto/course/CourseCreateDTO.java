package com.forumhub.api.dto.course;

import jakarta.validation.constraints.NotBlank;

public record CourseCreateDTO(
        @NotBlank
        String name,
        @NotBlank
        String category
) {}
