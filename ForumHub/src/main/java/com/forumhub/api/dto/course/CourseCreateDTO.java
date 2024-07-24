package com.forumhub.api.dto.course;

import jakarta.validation.constraints.NotBlank;

public record CourseCreateDTO(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Category is required")
        String category
) {}
