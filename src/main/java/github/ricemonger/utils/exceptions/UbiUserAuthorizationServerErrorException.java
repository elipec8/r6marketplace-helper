package github.ricemonger.utils.exceptions;

public class UbiUserAuthorizationServerErrorException extends RuntimeException {
    public UbiUserAuthorizationServerErrorException(String s) {
        super(s);
    }

    public UbiUserAuthorizationServerErrorException() {
        super();
    }
}
