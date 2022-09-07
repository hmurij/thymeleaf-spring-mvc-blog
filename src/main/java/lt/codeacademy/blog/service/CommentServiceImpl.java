package lt.codeacademy.blog.service;

import lt.codeacademy.blog.entity.Comment;
import lt.codeacademy.blog.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
