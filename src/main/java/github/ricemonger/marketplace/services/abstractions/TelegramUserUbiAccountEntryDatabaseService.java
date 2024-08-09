package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.UserAlreadyHasAnotherUbiAccountEntryException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void save(String chatId, UbiAccountEntry user) throws TelegramUserDoesntExistException, UserAlreadyHasAnotherUbiAccountEntryException;

    void deleteByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountWithTelegram> findAll();
}
