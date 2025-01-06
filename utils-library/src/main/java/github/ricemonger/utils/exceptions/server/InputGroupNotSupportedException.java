package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class InputGroupNotSupportedException extends ServerAbstractException {
    public InputGroupNotSupportedException(String message) {
        super(message);
    }
}
