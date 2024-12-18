package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryEntityDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
import github.ricemonger.utils.DTOs.UbiAccountEntryEntityDTOWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntryEntityDTO user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void saveAllUbiAccountStats(List<UbiAccountStatsEntityDTO> ubiAccounts);

    void deleteAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationEntryEntityDTO findAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountAuthorizationEntryWithTelegram> findAllAuthorizationInfoForTelegram();

    List<UbiAccountEntryEntityDTOWithTelegram> findAllForTelegram();
}
