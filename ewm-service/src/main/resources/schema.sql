DROP TABLE IF EXISTS events CASCADE ;
DROP TABLE IF EXISTS compilation CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS comment CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    user_id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    user_name  VARCHAR(255),
    user_email VARCHAR(255) UNIQUE
    );

CREATE TABLE IF NOT EXISTS categories
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) UNIQUE,
    CONSTRAINT pk_category PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS events
(
    event_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(120) NOT NULL,
    annotation TEXT NOT NULL,
    category_id BIGINT NOT NULL,
    paid BOOLEAN NOT NULL,
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    participant_limit Integer NOT NULL,
    state VARCHAR(50) NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    request_moderation BOOLEAN NOT NULL,
    confirmed_requests Integer,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (category_id) REFERENCES categories (id) ON delete CASCADE,
    FOREIGN KEY (initiator_id) REFERENCES users (user_id) ON delete CASCADE
    );

CREATE TABLE IF NOT EXISTS compilation
(
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    title VARCHAR(255),
    pinned BOOLEAN NOT NULL
    );

CREATE TABLE IF NOT EXISTS compilation_event
(
    event_id BIGINT NOT NULL,
    compilation_id BIGINT NOT NULL,
    PRIMARY KEY (compilation_id, event_id),
    FOREIGN KEY (event_id) REFERENCES events (event_id),
    FOREIGN KEY (compilation_id) REFERENCES compilation (id)
    );

CREATE TABLE IF NOT EXISTS requests
(
    request_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    event_id BIGINT NOT NULL,
    requester_id BIGINT NOT NULL,
    status VARCHAR(100) NOT NULL,
    FOREIGN KEY (event_id) REFERENCES events (event_id),
    FOREIGN KEY (requester_id) REFERENCES users (user_id),
    CONSTRAINT UQ_PARTICIPANT_PER_EVENT UNIQUE (requester_id, event_id)
    );
CREATE TABLE IF NOT EXISTS comment (
    comment_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    event_id BIGINT,
    text VARCHAR(2000) NOT NULL,
    author_id BIGINT,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE,
    FOREIGN KEY (author_id) REFERENCES users (user_id) ON delete CASCADE,
    FOREIGN KEY (comment_id) REFERENCES events (event_id) ON delete CASCADE
);
