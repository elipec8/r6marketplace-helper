package github.ricemonger.marketplace.services;


import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;

import java.util.Collection;

public interface TelegramUserRepositoryService {
    void saveUser(TelegramUser telegramUser);

    boolean userExistsById(String chatId);

    TelegramUser findUserById(String chatId);

    Collection<TelegramUser> findAllUsers();

    void saveInput(TelegramUserInput telegramUserInput);

    void saveInput(String chatId, InputState inputState, String value);

    void deleteAllInputsByChatId(String chatId);

    TelegramUserInput findInputByIdOrEmpty(String chatId, InputState inputState);
}
