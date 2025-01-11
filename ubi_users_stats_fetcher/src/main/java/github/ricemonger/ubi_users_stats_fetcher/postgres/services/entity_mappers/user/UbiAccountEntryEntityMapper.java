package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.dto_projections.UserAuthorizedUbiAccountProjection;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserAuthorizedUbiAccount;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UbiAccountEntryEntityMapper {

    public UserAuthorizedUbiAccount createUserUbiAccountEntry(UserAuthorizedUbiAccountProjection projection) {
        return new UserAuthorizedUbiAccount(
                projection.getUserId(),
                projection.getProfileId(),
                projection.getCreditAmount(),
                projection.getTicket(),
                projection.getSpaceId(),
                projection.getSessionId(),
                projection.getRememberDeviceTicket(),
                projection.getRememberMeTicket()
        );
    }
}
