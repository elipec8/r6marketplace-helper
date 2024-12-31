package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountEntryPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.UbiAccountEntryEntityMapper;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.marketplace.services.abstractions.TelegramUserUbiAccountEntryDatabaseService;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryAlreadyExistsException;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TelegramUserUbiAccountPostgresService implements TelegramUserUbiAccountEntryDatabaseService {

    private final UbiAccountEntryPostgresRepository ubiAccountEntryPostgresRepository;

    private final UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, UbiAccountAuthorizationEntry account) throws TelegramUserDoesntExistException, UbiAccountEntryAlreadyExistsException {
        UbiAccountEntryEntity ubiAccountEntryEntity = ubiAccountEntryPostgresRepository.findByUserTelegramUserChatId(chatId).orElse(null);

        if (ubiAccountEntryEntity != null && !ubiAccountEntryEntity.getUbiAccountStats().getUbiProfileId().equals(account.getUbiProfileId())) {
            throw new UbiAccountEntryAlreadyExistsException("User with chatId " + chatId + " already has another Ubi account");
        } else {
            ubiAccountEntryPostgresRepository.save(ubiAccountEntryEntityMapper.createEntityForTelegramUser(chatId, account));
        }
    }

    @Override
    @Transactional
    public void deleteByChatId(String chatId) throws TelegramUserDoesntExistException {
        ubiAccountEntryPostgresRepository.deleteByUserTelegramUserChatId(chatId);
    }

    @Override
    @Transactional(readOnly = true)
    public UbiAccountAuthorizationEntry findByChatId(String chatId) throws TelegramUserDoesntExistException, UbiAccountEntryDoesntExistException {
      return ubiAccountEntryPostgresRepository.findUbiAccountAuthorizationEntryByUserTelegramUserChatId(chatId).orElseThrow(() -> new UbiAccountEntryDoesntExistException("Ubi account entry for chatId " + chatId + " doesn't exist"));
    }
}
