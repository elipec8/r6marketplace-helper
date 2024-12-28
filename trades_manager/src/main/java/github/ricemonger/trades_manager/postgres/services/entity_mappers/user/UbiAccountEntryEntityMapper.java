package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import github.ricemonger.trades_manager.services.DTOs.UserUbiAccountEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    public UserUbiAccountEntry createUserUbiAccountEntry(UserUbiAccountEntryEntity entity) {
        return new UserUbiAccountEntry(
                entity.getId(),
                entity.getUbiAccountEntry().getProfileId_(),
                entity.getUbiAccountEntry().getUbiAccountStats().getCreditAmount(),
                entity.getUbiAccountEntry().getUbiAuthTicket(),
                entity.getUbiAccountEntry().getUbiSpaceId(),
                entity.getUbiAccountEntry().getUbiSessionId(),
                entity.getUbiAccountEntry().getUbiRememberDeviceTicket(),
                entity.getUbiAccountEntry().getUbiRememberMeTicket()
        );
    }
}
