package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void save(String chatId, UbiAccountEntry user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void deleteByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountWithTelegram> findAll();
}
