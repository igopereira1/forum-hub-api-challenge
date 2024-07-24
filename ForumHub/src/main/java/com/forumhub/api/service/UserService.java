package com.forumhub.api.service;

import com.forumhub.api.dto.user.UserCreateDTO;
import com.forumhub.api.dto.user.UserDetailsDTO;
import com.forumhub.api.model.User;
import com.forumhub.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDetailsDTO createUser(UserCreateDTO userCreateDTO) {
        String encodedPassword = passwordEncoder.encode(userCreateDTO.password());
        User user = new User();
        user.setLogin(userCreateDTO.login());
        user.setPassword(encodedPassword);
        user = userRepository.save(user);
        return new UserDetailsDTO(user);
    }
}