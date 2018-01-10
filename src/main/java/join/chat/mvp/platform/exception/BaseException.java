package join.chat.mvp.platform.exception;

public class BaseException extends RuntimeException {
    public BaseException(final String message) {
        super(message);
    }

    public BaseException(final String message, final Exception e) {
        super(message, e);
    }
}
