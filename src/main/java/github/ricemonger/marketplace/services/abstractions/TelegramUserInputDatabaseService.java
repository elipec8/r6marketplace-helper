package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;

import java.util.Collection;

public interface TelegramUserInputDatabaseService {
    void saveInput(TelegramUserInput telegramUserInput);

    void saveInput(String chatId, InputState inputState, String value);

    void deleteAllInputsByChatId(String chatId);

    TelegramUserInput findInputById(String chatId, InputState inputState);

    Collection<TelegramUserInput> findAllInputsByChatId(String s);
}
