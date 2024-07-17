package com.forumhub.api.controller;

import com.forumhub.api.dto.topic.TopicCreateDTO;
import com.forumhub.api.dto.topic.TopicDetailsDTO;
import com.forumhub.api.dto.topic.TopicUpdateDTO;
import com.forumhub.api.model.Topic;
import com.forumhub.api.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping
    public ResponseEntity createTopic(@RequestBody TopicCreateDTO topicCreateDTO, UriComponentsBuilder uriBuilder) {
        Topic createdTopic = topicService.createTopic(topicCreateDTO);
        var uri = uriBuilder.path("/api/topics/{id}").buildAndExpand(createdTopic.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicDetailsDTO(
                createdTopic.getId(),
                createdTopic.getTitle(),
                createdTopic.getMessage(),
                createdTopic.getCreatedAt(),
                createdTopic.getStatus()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity getTopicById(@PathVariable Long id) {
        Topic topic = topicService.getTopicById(id);
        return ResponseEntity.ok(topic);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTopic(@PathVariable Long id, @RequestBody TopicUpdateDTO topicUpdateDTO) {
        Topic updatedTopic = topicService.updateTopic(id, topicUpdateDTO);
        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTopic(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
}
