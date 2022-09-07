package lt.codeacademy.blog.controller;

import lt.codeacademy.blog.dto.BlogUserDto;
import lt.codeacademy.blog.exception.CommonException;
import lt.codeacademy.blog.service.BlogUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class BlogUserController {
    private final BlogUserService userService;

    public BlogUserController(BlogUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal) {
        if (principal != null) {
            return "redirect:/main";
        }
        model.addAttribute("newBlogUser", new BlogUserDto());
        return "/main-templates/new-user";
    }

    @PostMapping("/register")
    public String processNewUser(
            @Valid @ModelAttribute("newBlogUser") BlogUserDto blogUserDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "/main-templates/new-user";
        }
        userService.findByUserName(blogUserDto.getUsername())
                .ifPresent(blogUser -> {
                    throw new CommonException("common.error.user.exists");
                });
        userService.save(blogUserDto);
        return "redirect:/user-created";
    }

    @GetMapping("/user-created")
    public String userCreated() {
        return "/main-templates/user-created";
    }
}
