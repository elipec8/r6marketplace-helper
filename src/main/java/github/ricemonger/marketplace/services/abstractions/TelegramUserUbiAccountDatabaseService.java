package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;

import java.util.List;

public interface TelegramUserUbiAccountDatabaseService {
    void save(String chatId, UbiAccount user);

    void deleteByChatId(String chatId);

    UbiAccount findByChatId(String chatId);

    List<UbiAccountWithTelegram> findAll();
}
