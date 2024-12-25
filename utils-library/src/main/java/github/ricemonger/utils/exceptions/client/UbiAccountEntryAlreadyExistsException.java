package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class UbiAccountEntryAlreadyExistsException extends ClientAbstractException {
    public UbiAccountEntryAlreadyExistsException(String message) {
        super(message);
    }
}
