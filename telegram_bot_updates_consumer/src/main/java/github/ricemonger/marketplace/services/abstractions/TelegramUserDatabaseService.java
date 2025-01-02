package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;

import java.util.List;

public interface TelegramUserDatabaseService {

    void register(String chatId) throws TelegramUserAlreadyExistsException;

    boolean isRegistered(String chatId);

    void setUserInputGroup(String chatId, InputGroup inputGroup);

    void setUserInputState(String chatId, InputState inputState);

    void setUserInputStateAndGroup(String chatId, InputState inputState, InputGroup inputGroup);

    TelegramUserInputStateAndGroup findUserInputStateAndGroupById(String chatId) throws TelegramUserDoesntExistException;

    void setUserItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    void setUserItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException;

    void setUserItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException;

    void addUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException;

    void removeUserItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException;

    List<String> findAllUserItemShowAppliedFiltersNames(String chatId) throws TelegramUserDoesntExistException;

    ItemShowSettings findUserItemShowSettings(String chatId) throws TelegramUserDoesntExistException;

    void setUserTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    void setUserTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    TradeManagersSettings findUserTradeManagersSettings(String chatId) throws TelegramUserDoesntExistException;
}
