package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;

import java.util.List;

public interface TelegramUserInputDatabaseService {
    void save(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException;

    void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException;

    TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException;

    List<TelegramUserInput> findAllByChatId(String s) throws TelegramUserDoesntExistException;
}
