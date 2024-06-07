package github.ricemonger.marketplace.services.abstractions;


import github.ricemonger.utils.dtos.TelegramUser;

import java.util.Collection;

public interface TelegramUserDatabaseService {
    void saveUser(TelegramUser telegramUser);

    boolean userExistsById(String chatId);

    TelegramUser findUserById(String chatId);

    Collection<TelegramUser> findAllUsers();
}
