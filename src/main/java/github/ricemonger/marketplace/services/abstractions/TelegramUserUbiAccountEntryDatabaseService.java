package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountStatsEntityDTO;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntry user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void saveAllUbiAccountStats(List<UbiAccountStatsEntityDTO> ubiAccounts);

    void deleteAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationEntry findAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountAuthorizationEntryWithTelegram> findAllAuthorizationInfoForTelegram();

    List<UbiAccountEntryWithTelegram> findAllForTelegram();
}
