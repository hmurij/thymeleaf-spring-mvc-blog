package lt.codeacademy.blog.service;

import lt.codeacademy.blog.dto.BlogUserDto;
import lt.codeacademy.blog.entity.BlogUser;
import lt.codeacademy.blog.repository.BlogUserRepository;
import lt.codeacademy.blog.utils.factory.BlogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static lt.codeacademy.blog.utils.mapper.BlogMapper.mapToBlogUser;

@Service
public class BlogUserServiceImpl implements BlogUserService {

    private final BlogUserRepository repository;

    public BlogUserServiceImpl(BlogUserRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void init() {
        BlogFactory.blogUsers.forEach(repository::save);
        repository.save(BlogFactory.generateBlogAdmin());
    }

    @Override
    public List<BlogUser> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(BlogUserDto blogUserDto) {
        repository.save(mapToBlogUser(blogUserDto));
    }

    @Override
    public Optional<BlogUser> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }
}
