package com.forumhub.api.dto.topic;

import com.forumhub.api.model.Status;
import java.time.LocalDateTime;

public record TopicDetailsDTO(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        Status status
) {}
