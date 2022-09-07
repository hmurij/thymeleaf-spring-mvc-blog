package lt.codeacademy.blog.controller;

import lt.codeacademy.blog.dto.CommentDto;
import lt.codeacademy.blog.entity.BlogUser;
import lt.codeacademy.blog.entity.Comment;
import lt.codeacademy.blog.entity.Post;
import lt.codeacademy.blog.exception.CommonException;
import lt.codeacademy.blog.exception.NotFoundException;
import lt.codeacademy.blog.service.BlogUserService;
import lt.codeacademy.blog.service.CommentService;
import lt.codeacademy.blog.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/")
public class CommentController {
    private final PostService postService;
    private final BlogUserService blogUserService;
    private final CommentService commentService;

    public CommentController(PostService postService, BlogUserService blogUserService, CommentService commentService) {
        this.postService = postService;
        this.blogUserService = blogUserService;
        this.commentService = commentService;
    }

    @PostMapping("/new-comment")
    public String newComment(
            @Valid @ModelAttribute("newComment") CommentDto comment,
            BindingResult bindingResult,
            Principal principal) {
        Post post = postService.getById(comment.getPostId())
                .orElseThrow(() -> new NotFoundException(comment.getPostId(), "notfound.post"));
        if (bindingResult.hasErrors() || principal == null) {
            return "redirect:/post/" + comment.getPostId();
        }
        BlogUser blogUser = blogUserService.findByUserName(principal.getName())
                .orElseThrow(() -> new CommonException("common.error.user.not.found"));
        post.getComments().add(new Comment(comment.getComment(), LocalDate.now(), blogUser, post));
        postService.save(post);
        return "redirect:/post/" + comment.getPostId();
    }

    @PostMapping("/update-comment")
    public String updateComment(
            @Valid @ModelAttribute("newComment") CommentDto comment,
            BindingResult bindingResult,
            Principal principal) {
        Comment updatedComment = commentService.findById(comment.getCommentId())
                .orElseThrow(() -> new CommonException("common.error.comment.not.found"));
        if (bindingResult.hasErrors()) {
            return "redirect:/post/" + updatedComment.getPost().getId();
        }
        if (principal == null ||
                (!principal.getName().equals("admin") && !principal.getName().equals(updatedComment.getBlogUser().getUserName()))) {
            return "redirect:/main";
        }
        updatedComment.setComment(comment.getComment());
        updatedComment.setCreatedOn(LocalDate.now());
        commentService.save(updatedComment);
        return "redirect:/post/" + updatedComment.getPost().getId();
    }

    @GetMapping("/post/delete-comment/{id}")
    public String deleteComment(@PathVariable("id") Long id, Principal principal) {
        Comment comment = commentService.findById(id)
                .orElseThrow(() -> new CommonException("common.error.comment.not.found"));
        if (principal == null ||
                (!principal.getName().equals("admin") && !principal.getName().equals(comment.getBlogUser().getUserName()))) {
            return "redirect:/main";
        }
        commentService.deleteById(id);
        return "redirect:/post/" + comment.getPost().getId();
    }
}
