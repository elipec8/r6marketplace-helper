package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class UbiUserAuthorizationServerErrorException extends ServerAbstractException {
    public UbiUserAuthorizationServerErrorException(String message) {
        super(message);
    }
}
