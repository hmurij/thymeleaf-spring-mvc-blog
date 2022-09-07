package lt.codeacademy.blog.exception;

public class NotFoundException extends RuntimeException {
    private final Long id;
    private final String message;

    public NotFoundException(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
