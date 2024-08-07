package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.dtos.ItemShowSettings;
import github.ricemonger.utils.dtos.ItemShownFieldsSettings;
import github.ricemonger.utils.dtos.TelegramUser;

import java.util.Collection;
import java.util.List;

public interface TelegramUserDatabaseService {

    void create(String chatId);

    void update(TelegramUser telegramUser);

    void setItemShowFewItemsInMessageFlag(String chatId, boolean flag);

    void setItemShowMessagesLimit(String chatId, Integer limit);

    void setItemShowFieldsSettings(String chatId, ItemShownFieldsSettings settings);

    void addItemShowAppliedFilter(String chatId, ItemFilter filter);

    void removeItemShowAppliedFilter(String chatId, String filterName);

    boolean existsById(String chatId);

    TelegramUser findUserById(String chatId);

    ItemShowSettings findUserSettingsById(String chatId);

    List<TelegramUser> findAllUsers();
}
