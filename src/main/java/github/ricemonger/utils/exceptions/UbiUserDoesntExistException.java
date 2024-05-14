package github.ricemonger.utils.exceptions;

public class UbiUserDoesntExistException extends RuntimeException {
    public UbiUserDoesntExistException(Exception e) {
        super(e);
    }
}
