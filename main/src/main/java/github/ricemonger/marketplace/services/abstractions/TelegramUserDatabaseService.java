package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.utils.DTOs.personal.*;
import github.ricemonger.utils.exceptions.client.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;

import java.util.List;

public interface TelegramUserDatabaseService {

    void create(String chatId) throws TelegramUserAlreadyExistsException;

    void update(TelegramUser telegramUser) throws TelegramUserDoesntExistException;

    void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    void setItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException;

    void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException;

    void addItemShowAppliedFilter(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException;

    void removeItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException;

    void setTradeManagersSettingsNewManagersAreActiveFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    void setTradeManagersSettingsManagingEnabledFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    boolean existsById(String chatId);

    TelegramUser findUserById(String chatId) throws TelegramUserDoesntExistException;

    TelegramUserInputStateAndGroup findUserInputStateAndGroupById(String chatId) throws TelegramUserDoesntExistException;

    ItemShowSettings findUserItemShowSettingsById(String chatId) throws TelegramUserDoesntExistException;

    TradeManagersSettings findUserTradeManagersSettingsById(String chatId) throws TelegramUserDoesntExistException;

    List<TelegramUser> findAllUsers();
}
