package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountStats;
import github.ricemonger.utils.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserUbiAccountPostgresService implements TelegramUserUbiAccountEntryDatabaseService {

    private final UbiAccountAuthorizationEntryPostgresRepository ubiAccountAuthorizationEntryRepository;

    private final UbiAccountStatsEntityPostgresRepository ubiAccountStatsRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final ItemPostgresRepository itemRepository;

    @Override
    @Transactional
    public void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntry account) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        UbiAccountEntryEntity ubiAccountAuthorizationEntry = ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(telegramUser.getChatId()).orElse(null);

        if (ubiAccountAuthorizationEntry != null && !ubiAccountAuthorizationEntry.getUbiAccountStats().getUbiProfileId().equals(account.getUbiProfileId())) {
            throw new UbiAccountEntryAlreadyExistsException("User with chatId " + chatId + " already has another Ubi account");
        } else {
            UbiAccountStatsEntity ubiAccountStatsEntity = ubiAccountStatsRepository
                    .findById(account.getUbiProfileId())
                    .orElse(ubiAccountStatsRepository.save(new UbiAccountStatsEntity(account.getUbiProfileId())));
            ubiAccountAuthorizationEntryRepository.save(new UbiAccountEntryEntity(telegramUser.getUser(), ubiAccountStatsEntity, account));
        }
    }

    @Override
    @Transactional
    public void saveAllUbiAccountStats(List<UbiAccountStats> ubiAccounts) {
        List<ItemEntity> existingItems = itemRepository.findAll();

        ubiAccountStatsRepository.saveAll(ubiAccounts.stream().map(ubiAccount -> new UbiAccountStatsEntity(ubiAccount, existingItems)).toList());
    }

    @Override
    @Transactional
    public void deleteAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        telegramUser.getUser().setUbiAccountAuthorizationEntry(null);
        telegramUserRepository.save(telegramUser);
    }

    @Override
    public UbiAccountAuthorizationEntry findAuthorizationInfoByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(telegramUser.getChatId())
                .orElseThrow(() -> new UbiAccountEntryDoesntExistException("User with chatId " + chatId + " doesn't have ubi account entry")).toUbiAccountAuthorizationEntry();
    }

    @Override
    public List<UbiAccountAuthorizationEntryWithTelegram> findAllAuthorizationInfoForTelegram() {
        return ubiAccountAuthorizationEntryRepository.findAll().stream().map(UbiAccountEntryEntity::toUbiAccountAuthorizationEntryWithTelegram).toList();
    }

    @Override
    @Transactional
    public List<UbiAccountEntryWithTelegram> findAllForTelegram() {
        return ubiAccountAuthorizationEntryRepository.findAll().stream().map(UbiAccountEntryEntity::toUbiAccountEntryWithTelegram).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
