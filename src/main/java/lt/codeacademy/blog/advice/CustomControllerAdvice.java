package lt.codeacademy.blog.advice;

import lt.codeacademy.blog.exception.CommonException;
import lt.codeacademy.blog.exception.NotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public String notFound(NotFoundException e, Model model) {
        model.addAttribute("id", e.getId());
        model.addAttribute("message", e.getMessage());
        return "error/not-found";
    }

    @ExceptionHandler(CommonException.class)
    public String commonError(CommonException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "error/common-error";
    }
}
