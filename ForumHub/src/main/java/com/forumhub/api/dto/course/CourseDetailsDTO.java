package com.forumhub.api.dto.course;

import com.forumhub.api.model.Course;

public record CourseDetailsDTO(
        Long id,
        String name,
        String category
) {
    public CourseDetailsDTO(Course course) {
        this(
                course.getId(),
                course.getName(),
                course.getCategory()
        );
    }
}