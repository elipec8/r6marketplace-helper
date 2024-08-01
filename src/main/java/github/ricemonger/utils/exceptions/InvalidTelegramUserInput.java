package github.ricemonger.utils.exceptions;

public class InvalidTelegramUserInput extends RuntimeException {
    public InvalidTelegramUserInput(String message) {
        super(message);
    }
}
