package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void save(String chatId, UbiAccountAuthorizationEntry user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void deleteByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;
}
