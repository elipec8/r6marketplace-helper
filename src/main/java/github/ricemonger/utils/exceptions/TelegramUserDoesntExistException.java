package github.ricemonger.utils.exceptions;

public class TelegramUserDoesntExistException extends RuntimeException {
    public TelegramUserDoesntExistException(Exception exception) {
        super(exception);
    }

    public TelegramUserDoesntExistException(String message) {
        super(message);
    }
}
