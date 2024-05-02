package github.ricemonger.telegramBot.client;

public class TelegramApiRuntimeException extends RuntimeException {
    public TelegramApiRuntimeException(Exception e) {
        super(e);
    }
}
