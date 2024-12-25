package github.ricemonger.utils.exceptions.server;

import github.ricemonger.utils.exceptions.ServerAbstractException;

public class UbiCredentialsInnerException extends ServerAbstractException {
    public UbiCredentialsInnerException(Exception e) {
        super(e);
    }
}
