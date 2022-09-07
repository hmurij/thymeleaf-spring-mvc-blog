package lt.codeacademy.blog.controller;

import lt.codeacademy.blog.dto.CommentDto;
import lt.codeacademy.blog.dto.PostDto;
import lt.codeacademy.blog.entity.Post;
import lt.codeacademy.blog.exception.CommonException;
import lt.codeacademy.blog.exception.NotFoundException;
import lt.codeacademy.blog.service.BlogUserService;
import lt.codeacademy.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

import static lt.codeacademy.blog.utils.mapper.BlogMapper.mapToPost;

@Controller
@RequestMapping("/")
public class BlogPostController {
    private final PostService postService;
    private final BlogUserService blogUserService;

    public BlogPostController(PostService postService, BlogUserService blogUserService) {
        this.postService = postService;
        this.blogUserService = blogUserService;
    }

    @GetMapping(value = {"/", "/main"})
    public String mainPage(Model model) {
        model.addAttribute("posts", postService.findAll());
        return "/main-templates/main";
    }

    @GetMapping(value = {"/post/{id}"})
    public String postPage(@PathVariable("id") Long id, Model model) {
        model.addAttribute(
                "post",
                postService.getById(id).orElseThrow(() -> new NotFoundException(id, "notfound.post"))
        );
        model.addAttribute("newComment", new CommentDto());
        return "/main-templates/post";
    }

    @GetMapping("/new-post")
    public String newPost(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/main";
        }
        model.addAttribute("newPost", new PostDto());
        return "/main-templates/new-post";
    }

    @PostMapping("/new-post")
    public String processNewPost(@Valid @ModelAttribute("newPost") PostDto newPost, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "/main-templates/new-post";
        }
        postService.save(mapToPost(
                newPost,
                blogUserService.findByUserName(principal.getName())
                        .orElseThrow(() -> new CommonException("common.error.user.not.found")))
        );
        return "redirect:/main";
    }

    @PostMapping("/update-post")
    public String updatePost(@Valid @ModelAttribute("post") Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/post/" + post.getId();
        }
        Post updatedPost = postService.getById(post.getId())
                .orElseThrow(() -> new NotFoundException(post.getId(), "notfound.post"));
        updatedPost.setContent(post.getContent());
        updatedPost.setUpdatedOn(LocalDate.now());
        postService.save(updatedPost);
        return "redirect:/post/" + post.getId();
    }

    @GetMapping(value = {"/post/delete/{id}"})
    public String deletePost(@PathVariable("id") Long id, Principal principal) {
        Post post = postService.getById(id).orElseThrow(() -> new NotFoundException(id, "notfound.post"));
        if (principal == null || (!principal.getName().equals("admin") && !principal.getName().equals(post.getBlogUser().getUserName()))) {
            return "redirect:/main";
        }
        postService.getById(id)
                .map(Post::getId)
                .ifPresent(postService::deleteById);
        return "redirect:/main";
    }
}
