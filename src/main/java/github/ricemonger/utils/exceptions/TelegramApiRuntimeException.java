package github.ricemonger.utils.exceptions;

public class TelegramApiRuntimeException extends RuntimeException {
    public TelegramApiRuntimeException(Exception e) {
        super(e);
    }
}
