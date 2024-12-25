package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class InvalidTelegramUserInputException extends ServerAbstractException {
    public InvalidTelegramUserInputException(String message) {
        super(message);
    }
}
