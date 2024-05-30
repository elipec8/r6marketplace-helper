package github.ricemonger.utils.exceptions;

public class UbiUserDoesntExistException extends RuntimeException {
    public UbiUserDoesntExistException(Exception e) {
        super(e);
    }

    public UbiUserDoesntExistException(String message) {
        super(message);
    }

    public UbiUserDoesntExistException() {
        super();
    }
}
