CREATE TABLE topics (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    message VARCHAR(255) NOT NULL,
    created_at DATETIME,
    status VARCHAR(100) NOT NULL,
    author_id BIGINT NOT NULL,
    course VARCHAR(100) NOT NULL,

    PRIMARY KEY (id),

    CONSTRAINT fk_topics_users_id FOREIGN KEY (author_id) REFERENCES users(id)
);