package github.ricemonger.utils.exceptions;

public class MissingCallbackPrefixInUserInputException extends RuntimeException {
    public MissingCallbackPrefixInUserInputException(String message) {
        super(message);
    }
}
