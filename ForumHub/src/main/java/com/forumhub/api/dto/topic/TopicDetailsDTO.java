package com.forumhub.api.dto.topic;

import com.forumhub.api.dto.answer.AnswerDetailsDTO;
import com.forumhub.api.dto.course.CourseDetailsDTO;
import com.forumhub.api.dto.user.UserDetailsDTO;
import com.forumhub.api.model.Status;
import com.forumhub.api.model.Topic;
import java.time.LocalDateTime;
import java.util.List;

public record TopicDetailsDTO(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        Status status,
        UserDetailsDTO author,
        CourseDetailsDTO course,
        List<AnswerDetailsDTO> answers
) {
    public TopicDetailsDTO(Topic topic) {
        this(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                new UserDetailsDTO(topic.getAuthor()),
                new CourseDetailsDTO(topic.getCourse()),
                topic.getAnswerList().stream().map(AnswerDetailsDTO::new).toList()
        );
    }
}