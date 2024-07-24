package com.forumhub.api.service;

import com.forumhub.api.dto.answer.AnswerCreateDTO;
import com.forumhub.api.dto.answer.AnswerDetailsDTO;
import com.forumhub.api.dto.answer.AnswerUpdateDTO;
import com.forumhub.api.model.Answer;
import com.forumhub.api.model.Status;
import com.forumhub.api.model.Topic;
import com.forumhub.api.model.User;
import com.forumhub.api.repository.AnswerRepository;
import com.forumhub.api.repository.TopicRepository;
import com.forumhub.api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AnswerDetailsDTO createAnswer(AnswerCreateDTO answerCreateDTO) {
        Topic topic = topicRepository.findById(answerCreateDTO.topicId())
                .orElseThrow(() -> new EntityNotFoundException("Topic not found with id: " + answerCreateDTO.topicId()));
        User author = (User) getCurrentUser();
        Answer answer = new Answer(answerCreateDTO);

        answer.setTopic(topic);
        answer.setAuthor(author);

        if (topic.getStatus() == Status.NAO_RESPONDIDO) {
            topic.setStatus(Status.RESPONDIDO);
            topicRepository.save(topic);
        }

        answer = answerRepository.save(answer);

        return new AnswerDetailsDTO(answer);
    }

    @Transactional(readOnly = true)
    public AnswerDetailsDTO getAnswerById(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + id));

        return new AnswerDetailsDTO(answer);
    }

    @Transactional
    public AnswerDetailsDTO updateAnswer(Long id, AnswerUpdateDTO answerUpdateDTO) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + id));

        answer.setMessage(answerUpdateDTO.message());
        answer.setSolution(answerUpdateDTO.solution());

        Answer updatedAnswer = answerRepository.save(answer);

        return new AnswerDetailsDTO(updatedAnswer);
    }

    @Transactional
    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found with id: " + id));

        answerRepository.delete(answer);
    }

    @Transactional(readOnly = true)
    public List<AnswerDetailsDTO> getAllAnswers() {
        List<Answer> answers = answerRepository.findAll();

        return answers.stream()
                .map(AnswerDetailsDTO::new)
                .toList();
    }

    private UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        return userRepository.findByLogin(login)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + login));
    }
}