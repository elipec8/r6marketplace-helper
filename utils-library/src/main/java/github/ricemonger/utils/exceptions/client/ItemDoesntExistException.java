package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class ItemDoesntExistException extends ClientAbstractException {

    public ItemDoesntExistException(String message) {
        super(message);
    }
}
