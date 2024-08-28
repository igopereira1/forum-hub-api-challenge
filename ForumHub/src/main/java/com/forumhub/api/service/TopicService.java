package com.forumhub.api.service;

import com.forumhub.api.dto.topic.TopicCreateDTO;
import com.forumhub.api.dto.topic.TopicDetailsDTO;
import com.forumhub.api.dto.topic.TopicUpdateDTO;
import com.forumhub.api.model.Topic;
import com.forumhub.api.model.User;
import com.forumhub.api.model.Course;
import com.forumhub.api.repository.TopicRepository;
import com.forumhub.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public TopicDetailsDTO createTopic(TopicCreateDTO topicCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User author = (User) userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        Course course;

        try {
            course = Course.valueOf(topicCreateDTO.course().toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException("Invalid course: " + topicCreateDTO.course());
        }

        Topic topic = new Topic(topicCreateDTO, author, course);
        topic = topicRepository.save(topic);

        return new TopicDetailsDTO(topic);
    }

    @Transactional(readOnly = true)
    public TopicDetailsDTO getTopicById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));

        return new TopicDetailsDTO(topic);
    }

    @Transactional
    public TopicDetailsDTO updateTopic(Long id, TopicUpdateDTO topicUpdateDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));

        topic.setTitle(topicUpdateDTO.title());
        topic.setMessage(topicUpdateDTO.message());

        topic = topicRepository.save(topic);

        return new TopicDetailsDTO(topic);
    }

    @Transactional
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + id));

        topicRepository.delete(topic);
    }

    @Transactional(readOnly = true)
    public List<TopicDetailsDTO> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();

        return topics.stream()
                .map(TopicDetailsDTO::new)
                .toList();
    }
}