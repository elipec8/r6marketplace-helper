package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class TelegramUserAlreadyExistsException extends ClientAbstractException {
    public TelegramUserAlreadyExistsException(String message) {
        super(message);
    }
}
