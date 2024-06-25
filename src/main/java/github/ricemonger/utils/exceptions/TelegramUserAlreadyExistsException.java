package github.ricemonger.utils.exceptions;

public class TelegramUserAlreadyExistsException extends RuntimeException {
    public TelegramUserAlreadyExistsException() {

    }

    public TelegramUserAlreadyExistsException(String message) {
        super(message);
    }
}
