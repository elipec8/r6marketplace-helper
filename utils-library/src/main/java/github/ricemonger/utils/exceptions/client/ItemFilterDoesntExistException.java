package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class ItemFilterDoesntExistException extends ClientAbstractException {
    public ItemFilterDoesntExistException() {
        super();
    }

    public ItemFilterDoesntExistException(String message) {
        super(message);
    }
}
