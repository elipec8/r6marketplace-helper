package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.UbiAccount;

import java.util.Collection;

public interface UbiAccountDatabaseService {
    void save(String chatId, UbiAccount user);

    void deleteById(String chatId, String email);

    UbiAccount findById(String chatId, String email);

    Collection<UbiAccount> findAll();
}
