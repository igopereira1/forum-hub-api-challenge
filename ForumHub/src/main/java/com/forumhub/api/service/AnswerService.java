package com.forumhub.api.service;

import com.forumhub.api.dto.answer.AnswerCreateDTO;
import com.forumhub.api.dto.answer.AnswerUpdateDTO;
import com.forumhub.api.model.Answer;
import com.forumhub.api.model.Topic;
import com.forumhub.api.model.User;
import com.forumhub.api.repository.AnswerRepository;
import com.forumhub.api.repository.TopicRepository;
import com.forumhub.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Answer createAnswer(AnswerCreateDTO answerCreateDTO) {
        Topic topic = topicRepository.findById(answerCreateDTO.topicId())
                .orElseThrow(() -> new IllegalArgumentException("Topic not found with id: " + answerCreateDTO.topicId()));

        User author = (User) getCurrentUser();
        Answer answer = new Answer(answerCreateDTO);
        answer.setTopic(topic);
        answer.setAuthor(author);

        return answerRepository.save(answer);
    }

    @Transactional(readOnly = true)
    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));
    }

    @Transactional
    public Answer updateAnswer(Long id, AnswerUpdateDTO answerUpdateDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));

        // Update answer properties
        answer.setMessage(answerUpdateDTO.message());
        answer.setSolution(answerUpdateDTO.solution());

        return answerRepository.save(answer);
    }

    @Transactional
    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));

        answerRepository.delete(answer);
    }

    @Transactional(readOnly = true)
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + login));
    }
}
