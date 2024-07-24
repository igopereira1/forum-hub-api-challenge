package com.forumhub.api.dto.user;

import com.forumhub.api.model.User;

public record UserDetailsDTO(
        Long id,
        String login
) {
    public UserDetailsDTO(User user) {
        this(
                user.getId(),
                user.getLogin()
        );
    }
}
