package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class TelegramUserInputDoesntExistException extends ServerAbstractException {
    public TelegramUserInputDoesntExistException(String message) {
        super(message);
    }
}
