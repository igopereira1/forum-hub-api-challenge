package com.forumhub.api.service;

import com.forumhub.api.dto.topic.TopicCreateDTO;
import com.forumhub.api.dto.topic.TopicUpdateDTO;
import com.forumhub.api.model.Course;
import com.forumhub.api.model.Topic;
import com.forumhub.api.model.User;
import com.forumhub.api.repository.CourseRepository;
import com.forumhub.api.repository.TopicRepository;
import com.forumhub.api.repository.UserRepository;
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
    private final CourseRepository courseRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository, CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public Topic createTopic(TopicCreateDTO topicCreateDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User author = (User) userRepository.findByLogin(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));

        Course course = courseRepository.findById(topicCreateDTO.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + topicCreateDTO.courseId()));

        Topic topic = new Topic(topicCreateDTO, author, course);
        return topicRepository.save(topic);
    }

    @Transactional(readOnly = true)
    public Topic getTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + id));
    }

    @Transactional
    public Topic updateTopic(Long id, TopicUpdateDTO topicUpdateDTO) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + id));

        // Update topic properties
        topic.setTitle(topicUpdateDTO.title());
        topic.setMessage(topicUpdateDTO.message());

        return topicRepository.save(topic);
    }

    @Transactional
    public void deleteTopic(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + id));

        topicRepository.delete(topic);
    }

    @Transactional(readOnly = true)
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
