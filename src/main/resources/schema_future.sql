CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL UNIQUE,
    text TEXT NOT NULL,
    like_count INT NOT NULL DEFAULT 0,
    comment_count INT NOT NULL DEFAULT 0
    );

CREATE TABLE IF NOT EXISTS tags (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    tag VARCHAR(50) NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text VARCHAR NOT NULL,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
    );

INSERT INTO posts (title, text, like_count, comment_count) VALUES
    ('Первый пост', 'Это содержимое первого поста о программировании на Java и Spring Boot.', 5, 2),
    ('Второй пост', 'Здесь мы обсуждаем лучшие практики работы с базами данных и SQL.', 12, 5),
    ('Третий пост', 'Руководство по созданию REST API с использованием Spring Framework.', 8, 3),
    ('Четвертый пост', 'Обзор новых возможностей в Java 21 и как их использовать.', 3, 1),
    ('Пятый пост', 'Настройка Docker для разработки и деплоя Spring приложений.', 15, 7);

INSERT INTO tags (post_id, tag) VALUES
    (1, 'java'), (1, 'spring'), (1, 'programming'),
    (2, 'database'), (2, 'sql'), (2, 'best-practices'),
    (3, 'rest'), (3, 'api'), (3, 'spring'),
    (4, 'java'), (4, 'new-features'), (4, 'jdk21'),
    (5, 'docker'), (5, 'devops'), (5, 'deployment');

INSERT INTO comments (text, post_id) VALUES
    ('Отличный пост, спасибо за подробное объяснение!', 1),
    ('Можно пример кода с обработкой исключений?', 1),
    ('Согласен с автором, Spring Boot действительно упрощает разработку.', 2),
    ('А есть ли аналогичные инструменты для Python?', 2),
    ('Спасибо, статья помогла решить проблему с настройкой БД.', 2),
    ('Очень полезная информация для начинающих разработчиков.', 3),
    ('Добавьте, пожалуйста, раздел про тестирование API.', 3),
    ('Когда выйдет следующая статья про security?', 3),
    ('Жду новых статей про Java 21!', 4),
    ('Docker это must-have для современного разработчика.', 5),
    ('Какие есть альтернативы Docker для микросервисов?', 5),
    ('Помогите с настройкой сети в docker-compose', 5);
