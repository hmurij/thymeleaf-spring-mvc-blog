package lt.codeacademy.blog.utils.factory;

import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lt.codeacademy.blog.entity.BlogUser;
import lt.codeacademy.blog.entity.Comment;
import lt.codeacademy.blog.entity.Post;
import org.ajbrown.namemachine.Name;
import org.ajbrown.namemachine.NameGenerator;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class BlogFactory {
    public final static List<BlogUser> blogUsers;
    public final static List<Post> posts;

    private static final int BLOG_USERS_MAX_SIZE = 10;
    private static final int BLOG_USERS_MIN_SIZE = 5;
    private static final int POSTS_MAX_SIZE = 30;
    private static final int POSTS_MIN_SIZE = 20;
    private static final int COMMENTS_MAX_SIZE = 10;
    private static final int COMMENTS_MIN_SIZE = 5;

    private static final NameGenerator generator = new NameGenerator();
    private static final Lorem lorem = LoremIpsum.getInstance();
    private static final Random random = new Random();

    static {
        blogUsers = generateBlogUsers();
        posts = generatePosts();
    }

    private BlogFactory() {
    }

    private static List<BlogUser> generateBlogUsers() {
        return Stream.generate(BlogFactory::generateBlogUser)
                .limit(random.nextInt(BLOG_USERS_MAX_SIZE - BLOG_USERS_MIN_SIZE) + BLOG_USERS_MIN_SIZE)
                .toList();
    }

    public static BlogUser generateBlogAdmin() {
        return new BlogUser(
                "admin",
                "ADMIN",
                "{bcrypt}$2a$10$JM11cOpmVZMhEIjwp4gfTuztM2YUEs7FbWJYrpG6pLDEk6NYib/TO",
                "admin@mail.com"
        );
    }

    private static BlogUser generateBlogUser() {
        Name name = generator.generateName();
        return new BlogUser(
                name.toString(),
                "USER",
                PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("password"),
                "%s.%s@mail.com".formatted(name.getFirstName(), name.getLastName())
        );
    }

    private static List<Post> generatePosts() {
        return Stream.generate(BlogFactory::generatePost)
                .limit(random.nextInt(POSTS_MAX_SIZE - POSTS_MIN_SIZE) + POSTS_MIN_SIZE)
                .peek(post -> post.getBlogUser().addPost(post))
                .peek(post -> post.setComments(BlogFactory.generateComments(post)))
                .toList();
    }

    private static Post generatePost() {
        return new Post(
                lorem.getTitle(2, 4),
                lorem.getParagraphs(2, 6),
                LocalDate.now().minusDays(random.nextInt(365) + 100),
                LocalDate.now().minusDays(random.nextInt(50)),
                blogUsers.get(random.nextInt(blogUsers.size())),
                null
        );
    }

    private static List<Comment> generateComments(Post post) {
        return Stream.generate(BlogFactory::generateComment)
                .limit(random.nextInt(COMMENTS_MAX_SIZE - COMMENTS_MIN_SIZE) + COMMENTS_MIN_SIZE)
                .peek(comment -> comment.getBlogUser().addComment(comment))
                .peek(comment -> comment.setPost(post))
                .toList();
    }

    private static Comment generateComment() {
        return new Comment(
                lorem.getWords(10, 100),
                LocalDate.now().minusDays(random.nextInt(100)),
                blogUsers.get(random.nextInt(blogUsers.size())),
                null
        );
    }
}
