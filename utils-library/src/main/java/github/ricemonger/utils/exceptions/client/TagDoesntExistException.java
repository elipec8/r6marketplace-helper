package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class TagDoesntExistException extends ClientAbstractException {
    public TagDoesntExistException(String message) {
        super(message);
    }
}
