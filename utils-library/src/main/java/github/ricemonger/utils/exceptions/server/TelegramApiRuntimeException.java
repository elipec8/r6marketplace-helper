package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class TelegramApiRuntimeException extends ServerAbstractException {
    public TelegramApiRuntimeException(Exception e) {
        super(e);
    }

    public TelegramApiRuntimeException(String message) {
        super(message);
    }
}
