package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class UnexpectedDirectCommandException extends ClientAbstractException {
    public UnexpectedDirectCommandException(String message) {
        super(message);
    }
}
