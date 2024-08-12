package github.ricemonger.utils.exceptions;

public class InvalidTelegramUserInputException extends RuntimeException {
    public InvalidTelegramUserInputException(String message) {
        super(message);
    }
}
