package github.ricemonger.utils.exceptions;

public class ServerAbstractException extends RuntimeException {
    public ServerAbstractException() {
        super();
    }

    public ServerAbstractException(Throwable cause) {
        super(cause);
    }

    public ServerAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerAbstractException(String message) {
        super(message);
    }
}
