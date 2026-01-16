CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL UNIQUE,
    text VARCHAR NOT NULL,
    --image BLOB,
    tags VARCHAR NOT NULL DEFAULT '[]',
    comments VARCHAR NOT NULL DEFAULT '[]',
    like_count INT NOT NULL DEFAULT 0
    );

INSERT INTO posts (title, text, tags) VALUES ('Пост про JSON', 'JSON (JavaScript Object Notation) — текстовый формат обмена данными','["java", "json", "rest"]');
INSERT INTO posts (title, text, tags, comments) VALUES ('Пост про H2', 'H2 — открытая кроссплатформенная СУБД, полностью написанная на языке Java','["java", "h2"]', '["комментарий 1", "комментарий 2"]');
INSERT INTO posts (title, text) VALUES ('Пост про море', 'Море это кайф!');
INSERT INTO posts (title, text, comments) VALUES ('Пост про солнце', 'Солнце это лето!', '["Комментарий про солнце"]');
