package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class UbiAccountEntryDoesntExistException extends ClientAbstractException {
    public UbiAccountEntryDoesntExistException(Exception e) {
        super(e);
    }

    public UbiAccountEntryDoesntExistException(String message) {
        super(message);
    }

    public UbiAccountEntryDoesntExistException() {
        super();
    }
}
