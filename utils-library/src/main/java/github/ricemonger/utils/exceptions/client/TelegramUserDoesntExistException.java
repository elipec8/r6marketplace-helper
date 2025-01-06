package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class TelegramUserDoesntExistException extends ClientAbstractException {
    public TelegramUserDoesntExistException(Exception exception) {
        super(exception);
    }

    public TelegramUserDoesntExistException(String message) {
        super(message);
    }
}
