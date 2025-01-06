package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class TradeByFiltersManagerDoesntExistException extends ClientAbstractException {
    public TradeByFiltersManagerDoesntExistException(String message) {
        super(message);
    }
}
