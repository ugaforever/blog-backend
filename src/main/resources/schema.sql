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