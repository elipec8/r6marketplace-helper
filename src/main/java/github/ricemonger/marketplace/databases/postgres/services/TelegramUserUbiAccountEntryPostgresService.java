package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiAccountEntryDoesntExistException;
import github.ricemonger.utils.exceptions.UserAlreadyHasUbiAccountEntryException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserUbiAccountEntryPostgresService implements TelegramUserUbiAccountEntryDatabaseService {

    private final UbiAccountEntryPostgresRepository ubiAccountEntryRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, UbiAccountEntry account) throws TelegramUserDoesntExistException, UserAlreadyHasUbiAccountEntryException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        UbiAccountEntryEntity ubiAccountEntry = ubiAccountEntryRepository.findByUserTelegramUserChatId(telegramUser.getChatId()).orElse(null);

        if (ubiAccountEntry != null && !ubiAccountEntry.getUbiProfileId().equals(account.getUbiProfileId())) {
            throw new UserAlreadyHasUbiAccountEntryException("User with chatId " + chatId + " already has another Ubi account");
        } else {
            ubiAccountEntryRepository.save(new UbiAccountEntryEntity(telegramUser.getUser(), account));
        }
    }

    @Override
    @Transactional
    public void deleteByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.getUser().setUbiAccountEntry(null);
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public UbiAccountEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return ubiAccountEntryRepository.findByUserTelegramUserChatId(telegramUser.getChatId())
                .orElseThrow(() -> new UbiAccountEntryDoesntExistException("User with chatId " + chatId + " doesn't exist")).toUbiAccount();
    }

    @Override
    public List<UbiAccountWithTelegram> findAll() {
        return ubiAccountEntryRepository.findAll().stream().map(UbiAccountEntryEntity::toUbiAccountWithTelegram).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
