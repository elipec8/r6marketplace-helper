package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class MissingCallbackPrefixInUserInputException extends ServerAbstractException {
    public MissingCallbackPrefixInUserInputException(String message) {
        super(message);
    }
}
