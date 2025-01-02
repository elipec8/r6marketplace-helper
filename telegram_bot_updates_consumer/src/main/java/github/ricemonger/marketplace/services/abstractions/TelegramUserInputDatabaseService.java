package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;

import java.util.List;

public interface TelegramUserInputDatabaseService {
    void save(String chatId, InputState inputState, String value) throws TelegramUserDoesntExistException;

    void deleteAllByChatId(String chatId) throws TelegramUserDoesntExistException;

    TelegramUserInput findById(String chatId, InputState inputState) throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException;

    List<TelegramUserInput> findAllByChatId(String chatId) throws TelegramUserDoesntExistException;
}
