package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    private final UserPostgresRepository userPostgresRepository;

    private final UbiAccountStatsEntityPostgresRepository ubiAccountStatsEntityPostgresRepository;

    public UbiAccountEntryEntity createEntityForTelegramUser(String chatId, UbiAccountAuthorizationEntry account) {
        UserEntity user = userPostgresRepository.findByTelegramUserChatId(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));
        UbiAccountStatsEntity ubiAccountStatsEntity = ubiAccountStatsEntityPostgresRepository.findById(account.getUbiProfileId()).orElse(null);

        if (ubiAccountStatsEntity == null) {
            ubiAccountStatsEntity = new UbiAccountStatsEntity();
            ubiAccountStatsEntity.setUbiProfileId(account.getUbiProfileId());
            ubiAccountStatsEntityPostgresRepository.save(ubiAccountStatsEntity);
        }

        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUser(user);
        ubiAccountEntryEntity.setEmail(account.getEmail());
        ubiAccountEntryEntity.setEncodedPassword(account.getEncodedPassword());
        ubiAccountEntryEntity.setUbiSessionId(account.getUbiSessionId());
        ubiAccountEntryEntity.setUbiSpaceId(account.getUbiSpaceId());
        ubiAccountEntryEntity.setUbiAuthTicket(account.getUbiAuthTicket());
        ubiAccountEntryEntity.setUbiRememberDeviceTicket(account.getUbiRememberDeviceTicket());
        ubiAccountEntryEntity.setUbiRememberMeTicket(account.getUbiRememberMeTicket());
        ubiAccountEntryEntity.setUbiAccountStats(ubiAccountStatsEntity);
        return ubiAccountEntryEntity;
    }

    public UbiAccountAuthorizationEntry createUbiAccountAuthorizationEntry(UbiAccountEntryEntity entity) {
        return new UbiAccountAuthorizationEntry(
                entity.getProfileId_(),
                entity.getEmail(),
                entity.getEncodedPassword(),
                entity.getUbiSessionId(),
                entity.getUbiSpaceId(),
                entity.getUbiAuthTicket(),
                entity.getUbiRememberDeviceTicket(),
                entity.getUbiRememberMeTicket());
    }
}
