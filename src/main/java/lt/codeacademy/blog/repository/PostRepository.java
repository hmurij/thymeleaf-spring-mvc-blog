package lt.codeacademy.blog.repository;

import lt.codeacademy.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
