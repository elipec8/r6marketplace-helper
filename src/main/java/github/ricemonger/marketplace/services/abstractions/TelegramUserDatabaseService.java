package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.exceptions.TelegramUserAlreadyExistsException;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;

import java.util.List;

public interface TelegramUserDatabaseService {

    void create(String chatId) throws TelegramUserAlreadyExistsException;

    void update(TelegramUser telegramUser) throws TelegramUserDoesntExistException;

    void setItemShowFewItemsInMessageFlag(String chatId, boolean flag) throws TelegramUserDoesntExistException;

    void setItemShowMessagesLimit(String chatId, Integer limit) throws TelegramUserDoesntExistException;

    void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings) throws TelegramUserDoesntExistException;

    void addItemShowAppliedFilter(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException;

    void removeItemShowAppliedFilter(String chatId, String filterName) throws TelegramUserDoesntExistException;

    boolean existsById(String chatId);

    TelegramUser findUserById(String chatId) throws TelegramUserDoesntExistException;

    ItemShowSettings findUserSettingsById(String chatId) throws TelegramUserDoesntExistException;

    List<TelegramUser> findAllUsers();
}
