package github.ricemonger.utils.exceptions;

public abstract class ClientAbstractException extends RuntimeException {
    public ClientAbstractException() {
        super();
    }

    public ClientAbstractException(Throwable cause) {
        super(cause);
    }

    public ClientAbstractException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientAbstractException(String message) {
        super(message);
    }
}
