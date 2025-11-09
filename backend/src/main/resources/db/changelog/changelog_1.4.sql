-- liquibase formatted sql

-- changeset Yura:created-favorite_books-table
CREATE TABLE favorite_books
(
    book_id   BIGINT  NOT NULL,
    user_uuid VARCHAR NOT NULL,
    CONSTRAINT pk_favorite_books PRIMARY KEY (book_id, user_uuid)
);

-- changeset Yyra:created-fk-to-books-table
ALTER TABLE favorite_books
    ADD CONSTRAINT FK_FAVORITE_BOOKS_ON_BOOKS FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE;

-- changeset Yura:created-fk-to-users-table
ALTER TABLE favorite_books
    ADD CONSTRAINT "FK_FAVORITE_BOOKS_ON_USERS" FOREIGN KEY (user_uuid) REFERENCES users (uuid) ON DELETE CASCADE;

