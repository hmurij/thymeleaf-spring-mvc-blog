package lt.codeacademy.blog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CommentDto {
    @NotBlank
    @Size(min = 2)
    private String comment;
    private Long commentId;
    private Long postId;

    public CommentDto() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
