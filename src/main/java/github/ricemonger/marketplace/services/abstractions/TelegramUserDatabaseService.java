package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;

import java.util.Collection;

public interface TelegramUserDatabaseService {
    void save(TelegramUser telegramUser);

    void setItemShowFewItemsInMessageFlag(String chatId, boolean flag);

    void setItemShowMessagesLimit(String chatId, Integer limit);

    void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings);

    void addItemShowAppliedFilter(String chatId, ItemFilter filter);

    void removeItemShowAppliedFilter(String chatId, String filterName);

    boolean existsById(String chatId);

    TelegramUser findUserById(String chatId);

    ItemShowSettings findUserSettingsById(String chatId);

    Collection<TelegramUser> findAllUsers();
}
