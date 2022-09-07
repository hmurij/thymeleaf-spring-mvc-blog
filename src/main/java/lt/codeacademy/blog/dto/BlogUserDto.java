package lt.codeacademy.blog.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class BlogUserDto {
    @NotEmpty
    @Size(min = 2, max = 50)
    private String username;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min = 2, max = 50)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
