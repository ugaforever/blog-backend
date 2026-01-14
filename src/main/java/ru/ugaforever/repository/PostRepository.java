package ru.ugaforever.repository;

import ru.ugaforever.model.Post;
import java.util.List;

public interface PostRepository {
    List<Post> findAll();
}