package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.DTOs.UbiAccountAuthorizationDTO;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

import java.util.List;

public interface TelegramUserUbiAccountEntryDatabaseService {
    void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationDTO user) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException;

    void deleteByChatId(String chatId) throws TelegramUserDoesntExistException;

    UbiAccountAuthorizationDTO findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException;

    List<UbiAccountAuthorizationEntryWithTelegram> findAll();
}
