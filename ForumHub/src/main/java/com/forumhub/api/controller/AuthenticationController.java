package com.forumhub.api.controller;

import com.forumhub.api.dto.authentication.AuthenticationDataDTO;
import com.forumhub.api.dto.authentication.TokenDTO;
import com.forumhub.api.infra.security.TokenService;
import com.forumhub.api.model.User;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid AuthenticationDataDTO authenticationData) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(authenticationData.login(), authenticationData.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}