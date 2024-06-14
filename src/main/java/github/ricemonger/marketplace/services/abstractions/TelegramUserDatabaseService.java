package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;

import java.util.Collection;

public interface TelegramUserDatabaseService {
    void saveUser(TelegramUser telegramUser);

    boolean userExistsById(String chatId);

    TelegramUser findUserById(String chatId);

    Collection<TelegramUser> findAllUsers();

    ItemShowSettings findUserSettingsById(String chatId);

    void setItemShowFewItemsInMessageFlag(String chatId, boolean flag);

    void setItemShowMessagesLimit(String chatId, Integer limit);

    void setItemShowSettings(String chatId, ItemShownFieldsSettings settings);

    void addItemShowAppliedFilter(String chatId, ItemFilter filter);

    void removeItemShowAppliedFilter(String chatId, String filterName);
}
