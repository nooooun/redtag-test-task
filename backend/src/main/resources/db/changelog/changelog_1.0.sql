-- liquibase formatted sql

-- changeset Yura:created-users-table
CREATE TABLE users
(
    uuid          VARCHAR      NOT NULL,
    username      VARCHAR(255),
    email         VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role          VARCHAR(255),
    created_at    TIMESTAMP WITHOUT TIME ZONE,
    modified_at   TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (uuid)
);

-- changeset Yura:created-email-uc
ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

-- changeset Yura:created-password_hash-uc
ALTER TABLE users
    ADD CONSTRAINT uc_users_password_hash UNIQUE (password_hash);

