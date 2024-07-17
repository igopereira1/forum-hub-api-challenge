package com.forumhub.api.controller;

import com.forumhub.api.dto.answer.AnswerCreateDTO;
import com.forumhub.api.dto.answer.AnswerUpdateDTO;
import com.forumhub.api.model.Answer;
import com.forumhub.api.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody AnswerCreateDTO answerCreateDTO, UriComponentsBuilder uriBuilder) {
        Answer createdAnswer = answerService.createAnswer(answerCreateDTO);
        return ResponseEntity.created(uriBuilder.path("/api/answers/{id}").buildAndExpand(createdAnswer.getId()).toUri())
                .body(createdAnswer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswerById(@PathVariable Long id) {
        Answer answer = answerService.getAnswerById(id);
        return ResponseEntity.ok(answer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody AnswerUpdateDTO answerUpdateDTO) {
        Answer updatedAnswer = answerService.updateAnswer(id, answerUpdateDTO);
        return ResponseEntity.ok(updatedAnswer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Answer>> getAllAnswers() {
        List<Answer> answers = answerService.getAllAnswers();
        return ResponseEntity.ok(answers);
    }
}
