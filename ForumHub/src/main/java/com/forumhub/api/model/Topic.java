package com.forumhub.api.model;

import com.forumhub.api.dto.topic.TopicCreateDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Topic")
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Answer> answerList;

    public Topic(TopicCreateDTO topicCreateDTO, User author, Course course) {
        this.title = topicCreateDTO.title();
        this.message = topicCreateDTO.message();
        this.author = author;
        this.course = course;
        this.status = Status.NAO_RESPONDIDO;
        this.createdAt = LocalDateTime.now();
    }
}
