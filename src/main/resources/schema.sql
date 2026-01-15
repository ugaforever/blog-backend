-- Таблица с постами
create table if not exists posts(
    id bigserial primary key,
    title varchar(256) not null);
    /*,
    text varchar(256) not null,
    age integer not null,
    active boolean not null);*/

/*{
        "id": 1,
        "title": "Название поста 1",
        "text": "Текст поста в формате Markdown...",
        "tags": ["tag_1", "tag_2"],
        "likesCount": 5,
        "commentsCount": 1
      },*/


    /*
    -- Таблица постов
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_published BOOLEAN DEFAULT TRUE
);

-- Таблица комментариев
CREATE TABLE IF NOT EXISTS comments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    author VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

-- Индексы для оптимизации запросов
CREATE INDEX idx_post_id ON comments(post_id);
CREATE INDEX idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX idx_comments_created_at ON comments(created_at DESC);
    */