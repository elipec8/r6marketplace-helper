package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountStats;
import github.ricemonger.utils.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntry user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void saveAllUbiAccountStats(List<UbiAccountStats> ubiAccounts);

    void deleteAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationEntry findAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountAuthorizationEntryWithTelegram> findAllAuthorizationInfoForTelegram();

    List<UbiAccountEntryWithTelegram> findAllForTelegram();
}
