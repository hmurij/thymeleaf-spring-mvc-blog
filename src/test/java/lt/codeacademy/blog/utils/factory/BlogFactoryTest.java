package lt.codeacademy.blog.utils.factory;

import org.junit.jupiter.api.Test;

class BlogFactoryTest {

    @Test
    void blogUsers_blogFactory_forEach() {
        BlogFactory.blogUsers.forEach(System.out::println);
    }

    @Test
    void posts_blogFactory_forEach() {
        BlogFactory.posts.forEach(post -> System.out.println(post + "\n"));
    }

}
