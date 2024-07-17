CREATE TABLE topics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    message VARCHAR(255) NOT NULL,
    created_at DATETIME,
    status VARCHAR(100) NOT NULL,
    author_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_topics_courses_id FOREIGN KEY (course_id) REFERENCES courses(id),
    CONSTRAINT fk_topics_users_id FOREIGN KEY (author_id) REFERENCES users(id)
);
