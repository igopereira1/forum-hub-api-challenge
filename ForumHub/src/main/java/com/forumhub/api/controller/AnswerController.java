package com.forumhub.api.controller;

import com.forumhub.api.dto.answer.AnswerCreateDTO;
import com.forumhub.api.dto.answer.AnswerDetailsDTO;
import com.forumhub.api.dto.answer.AnswerUpdateDTO;
import com.forumhub.api.service.AnswerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
@SecurityRequirement(name = "bearerAuth")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity createAnswer(@RequestBody AnswerCreateDTO answerCreateDTO, UriComponentsBuilder uriBuilder) {
        AnswerDetailsDTO newAnswer = answerService.createAnswer(answerCreateDTO);
        var uri = uriBuilder.path("/api/answers/{id}").buildAndExpand(newAnswer.id()).toUri();
        return ResponseEntity.created(uri).body(newAnswer);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnswerById(@PathVariable Long id) {
        AnswerDetailsDTO answer = answerService.getAnswerById(id);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateAnswer(@PathVariable Long id, @RequestBody AnswerUpdateDTO answerUpdateDTO) {
        AnswerDetailsDTO updatedAnswer = answerService.updateAnswer(id, answerUpdateDTO);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAllAnswers() {
        List<AnswerDetailsDTO> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }
}