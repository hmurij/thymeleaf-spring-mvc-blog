package lt.codeacademy.blog.service;

import lt.codeacademy.blog.entity.Post;
import lt.codeacademy.blog.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    public List<Post> findAll() {
        return repository.findAll();
    }

    @Override
    public Post save(Post post) {
        return repository.save(post);
    }

    public Optional<Post> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
