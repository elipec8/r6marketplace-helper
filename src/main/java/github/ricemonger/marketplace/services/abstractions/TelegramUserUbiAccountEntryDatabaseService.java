package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.dtos.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntry user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void deleteByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountAuthorizationEntryWithTelegram> findAll();
}
