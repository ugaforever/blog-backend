DELETE FROM posts;

INSERT INTO posts (title, text, tags) VALUES ('Пост про JSON', 'JSON (JavaScript Object Notation) — текстовый формат обмена данными','["java", "json", "rest"]');
INSERT INTO posts (title, text, tags, comments) VALUES ('Пост про H2', 'H2 — открытая кроссплатформенная СУБД, полностью написанная на языке Java','["java", "h2"]', '["комментарий 1", "комментарий 2"]');
INSERT INTO posts (title, text) VALUES ('Пост про море', 'Море это кайф!');
INSERT INTO posts (title, text, comments) VALUES ('Пост про солнце', 'Солнце это лето!', '["Комментарий про солнце"]');
