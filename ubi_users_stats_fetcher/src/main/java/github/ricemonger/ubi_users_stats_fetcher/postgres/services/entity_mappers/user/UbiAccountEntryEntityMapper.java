package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserUbiAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    public UserUbiAccount createUserUbiAccountEntry(UserUbiAccountEntryEntity entity) {
        return new UserUbiAccount(
                entity.getId(),
                entity.getProfileId_(),
                entity.getCreditAmount_(),
                entity.getUbiAuthTicket_(),
                entity.getUbiSpaceId_(),
                entity.getUbiSessionId_(),
                entity.getUbiRememberDeviceTicket_(),
                entity.getUbiRememberMeTicket_()
        );
    }
}
