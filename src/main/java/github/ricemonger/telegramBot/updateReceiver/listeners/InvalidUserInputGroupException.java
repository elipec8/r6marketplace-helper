package github.ricemonger.telegramBot.updateReceiver.listeners;

public class InvalidUserInputGroupException extends RuntimeException {
    public InvalidUserInputGroupException(String message) {
        super(message);
    }
}
