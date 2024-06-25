package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;

import java.util.Collection;

public interface TelegramUserInputDatabaseService {
    void save(String chatId, InputState inputState, String value);

    void deleteAllByChatId(String chatId);

    TelegramUserInput findById(String chatId, InputState inputState);

    Collection<TelegramUserInput> findAllByChatId(String s);
}
