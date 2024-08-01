package github.ricemonger.utils.exceptions;

public class UbiAccountEntryDoesntExistException extends RuntimeException {
    public UbiAccountEntryDoesntExistException(Exception e) {
        super(e);
    }

    public UbiAccountEntryDoesntExistException(String message) {
        super(message);
    }

    public UbiAccountEntryDoesntExistException() {
        super();
    }
}
