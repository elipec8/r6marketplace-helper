package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class TradeByItemIdManagerDoesntExistException extends ClientAbstractException {
    public TradeByItemIdManagerDoesntExistException(String message) {
        super(message);
    }
}
