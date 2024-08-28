package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class UnexpectedCallbackCommandException extends ServerAbstractException {
    public UnexpectedCallbackCommandException(String message) {
        super(message);
    }
}
