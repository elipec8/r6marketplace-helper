package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiAccountStatsEntityPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.UserPostgresRepository;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntryWithTelegram;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountEntryWithTelegram;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    private final UserPostgresRepository userPostgresRepository;

    private final UbiAccountStatsEntityPostgresRepository ubiAccountStatsEntityPostgresRepository;

    private final UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    public UbiAccountEntryEntity createEntityForTelegramUser(String chatId, UbiAccountAuthorizationEntry account) {
        UserEntity user = userPostgresRepository.findByTelegramUserChatId(chatId);
        UbiAccountStatsEntity ubiAccountStatsEntity =
                ubiAccountStatsEntityPostgresRepository.findById(account.getUbiProfileId()).orElse(ubiAccountStatsEntityPostgresRepository.save(new UbiAccountStatsEntity(account.getUbiProfileId())));

        return new UbiAccountEntryEntity(
                user,
                account.getEmail(),
                account.getEncodedPassword(),
                account.getUbiSessionId(),
                account.getUbiSpaceId(),
                account.getUbiAuthTicket(),
                account.getUbiRememberDeviceTicket(),
                account.getUbiRememberMeTicket(),
                ubiAccountStatsEntity);
    }

    public UbiAccountEntryEntity createEntity(UserEntity userEntity, UbiAccountStatsEntity ubiAccountStatsEntity, UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry) {
        return new UbiAccountEntryEntity(
                userEntity,
                ubiAccountAuthorizationEntry.getEmail(),
                ubiAccountAuthorizationEntry.getEncodedPassword(),
                ubiAccountAuthorizationEntry.getUbiSessionId(),
                ubiAccountAuthorizationEntry.getUbiSpaceId(),
                ubiAccountAuthorizationEntry.getUbiAuthTicket(),
                ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket(),
                ubiAccountAuthorizationEntry.getUbiRememberMeTicket(),
                ubiAccountStatsEntity);
    }

    public UbiAccountAuthorizationEntryWithTelegram createUbiAccountAuthorizationEntryWithTelegram(UbiAccountEntryEntity entity) {
        return new UbiAccountAuthorizationEntryWithTelegram(
                entity.getUser().getTelegramUser().getChatId(),
                entity.getUser().getPrivateNotificationsEnabledFlag(),
                createUbiAccountAuthorizationEntry(entity));
    }

    public UbiAccountEntryWithTelegram createUbiAccountEntryWithTelegram(UbiAccountEntryEntity entity) {
        UbiAccountEntry ubiAccountEntry = new UbiAccountEntry(
                createUbiAccountAuthorizationEntry(entity),
                ubiAccountStatsEntityMapper.createDTO(entity.getUbiAccountStats())
        );
        return new UbiAccountEntryWithTelegram(
                entity.getUser().getTelegramUser().getChatId(),
                entity.getUser().getPrivateNotificationsEnabledFlag(),
                ubiAccountEntry);
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
