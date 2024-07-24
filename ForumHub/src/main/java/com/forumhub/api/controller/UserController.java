package com.forumhub.api.controller;

import com.forumhub.api.dto.user.UserCreateDTO;
import com.forumhub.api.dto.user.UserDetailsDTO;
import com.forumhub.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        UserDetailsDTO user = userService.createUser(userCreateDTO);
        return ResponseEntity.ok(user);
    }
}