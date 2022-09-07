package lt.codeacademy.blog.utils.mapper;

import lt.codeacademy.blog.dto.BlogUserDto;
import lt.codeacademy.blog.dto.PostDto;
import lt.codeacademy.blog.entity.BlogUser;
import lt.codeacademy.blog.entity.Post;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.time.LocalDate;
import java.util.ArrayList;

public class BlogMapper {
    public static BlogUser mapToBlogUser(BlogUserDto blogUserDto) {
        return new BlogUser(
                blogUserDto.getUsername(),
                "USER",
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(blogUserDto.getPassword()),
                blogUserDto.getEmail());
    }

    public static Post mapToPost(PostDto postDto, BlogUser blogUser) {
        return new Post(
                postDto.getTitle(),
                postDto.getContent(),
                LocalDate.now(),
                LocalDate.now(),
                blogUser,
                new ArrayList<>()
        );
    }
}
