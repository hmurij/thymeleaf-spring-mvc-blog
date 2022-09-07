package lt.codeacademy.blog.service;

import lt.codeacademy.blog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> findAll();

    Post save(Post post);

    Optional<Post> getById(Long id);

    void deleteById(Long id);
}
