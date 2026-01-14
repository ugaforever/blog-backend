package ru.ugaforever.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ugaforever.model.Post;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @GetMapping
    public List<Post> getUsers() {
        List<Post> users = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            Post post = new Post();
            post.setId(i);
            post.setTitle("title" + i);
            post.setText("text" + i);
            post.setLikesCount((int) i);
            users.add(post);
        }

        return users;
    }
}
