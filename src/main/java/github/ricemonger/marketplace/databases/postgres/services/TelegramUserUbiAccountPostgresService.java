package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountAuthorizationEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.user.UbiAccountEntryEntityFactory;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.user.UbiAccountStatsEntityFactory;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountEntryWithTelegram;
import github.ricemonger.utils.DTOs.UbiAccountStatsEntityDTO;
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

    private final UbiAccountEntryEntityFactory ubiAccountEntryEntityFactory;

    private final UbiAccountStatsEntityFactory ubiAccountStatsEntityFactory;

    @Override
    @Transactional
    public void saveAuthorizationInfo(String chatId, UbiAccountAuthorizationEntry account) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException {
        UbiAccountEntryEntity ubiAccountAuthorizationEntry = ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(chatId).orElse(null);

        if (ubiAccountAuthorizationEntry != null && !ubiAccountAuthorizationEntry.getUbiAccountStats().getUbiProfileId().equals(account.getUbiProfileId())) {
            throw new UbiAccountEntryAlreadyExistsException("User with chatId " + chatId + " already has another Ubi account");
        } else {
            ubiAccountAuthorizationEntryRepository.save(ubiAccountEntryEntityFactory.createEntityForTelegramUser(chatId, account));
        }
    }

    @Override
    @Transactional
    public void saveAllUbiAccountStats(List<UbiAccountStatsEntityDTO> ubiAccounts) {
        ubiAccountStatsRepository.saveAll(ubiAccountStatsEntityFactory.createEntities(ubiAccounts));
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

        return ubiAccountEntryEntityFactory.createUbiAccountAuthorizationEntry(ubiAccountAuthorizationEntryRepository.findByUserTelegramUserChatId(telegramUser.getChatId())
                .orElseThrow(() -> new UbiAccountEntryDoesntExistException("User with chatId " + chatId + " doesn't have ubi account entry")));
    }

    @Override
    public List<UbiAccountAuthorizationEntryWithTelegram> findAllAuthorizationInfoForTelegram() {
        return ubiAccountAuthorizationEntryRepository.findAll().stream().map(ubiAccountEntryEntityFactory::createUbiAccountAuthorizationEntryWithTelegram).toList();
    }

    @Override
    @Transactional
    public List<UbiAccountEntryWithTelegram> findAllForTelegram() {
        return ubiAccountAuthorizationEntryRepository.findAll().stream().map(ubiAccountEntryEntityFactory::createUbiAccountEntryWithTelegram).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
