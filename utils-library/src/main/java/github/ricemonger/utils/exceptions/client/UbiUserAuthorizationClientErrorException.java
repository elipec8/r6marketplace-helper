package github.ricemonger.utils.exceptions.client;

import github.ricemonger.utils.exceptions.ClientAbstractException;

public class UbiUserAuthorizationClientErrorException extends ClientAbstractException {
    public UbiUserAuthorizationClientErrorException(String message) {
        super(message);
    }
}
