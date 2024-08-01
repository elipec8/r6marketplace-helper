package github.ricemonger.utils.exceptions;

public class TelegramUserInputDoesntExistException extends RuntimeException {
    public TelegramUserInputDoesntExistException(String message) {
        super(message);
    }
}
