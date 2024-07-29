package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountDatabaseService;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserUbiAccountPostgresService implements TelegramUserUbiAccountDatabaseService {

    private final UbiAccountPostgresRepository ubiAccountPostgresRepository;

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    @Override
    @Transactional
    public void save(String chatId, UbiAccount account) {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        // if(user.getUser().getUbiAccount() == null) {
        ubiAccountPostgresRepository.save(new UbiAccountEntity(user.getUser(), account));
        // }
        // else{
        //   throw new UbiUserDoesntExistException("User with chatId " + chatId + " already has an account");
        // }
    }

    @Override
    // @Transactional
    public void update(String chatId, UbiAccount account) {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        if (user.getUser().getUbiAccount() == null) {
            throw new UbiUserDoesntExistException("User with chatId " + chatId + " already has an account");
        } else {
            ubiAccountPostgresRepository.save(new UbiAccountEntity(user.getUser(), account));
        }
    }

    @Override
    @Transactional
    public void deleteByChatId(String chatId) {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        user.getUser().setUbiAccount(null);
    }

    @Override
    public UbiAccount findByChatId(String chatId) throws UbiUserDoesntExistException {
        TelegramUserEntity user = getTelegramUserEntityByIdOrThrow(chatId);

        return ubiAccountPostgresRepository.findByUserTelegramUserChatId(user.getChatId())
                .orElseThrow(() -> new UbiUserDoesntExistException("User with chatId " + chatId + " doesn't exist")).toUbiAccount();
    }

    @Override
    public List<UbiAccountWithTelegram> findAll() {
        return ubiAccountPostgresRepository.findAll().stream().map(UbiAccountEntity::toUbiAccountWithTelegram).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserPostgresRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
