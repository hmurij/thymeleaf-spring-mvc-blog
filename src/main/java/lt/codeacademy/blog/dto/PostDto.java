package lt.codeacademy.blog.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {
    @NotEmpty
    @Size(min = 5, max = 50)
    private String title;
    @NotEmpty
    @Size(min = 250)
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
