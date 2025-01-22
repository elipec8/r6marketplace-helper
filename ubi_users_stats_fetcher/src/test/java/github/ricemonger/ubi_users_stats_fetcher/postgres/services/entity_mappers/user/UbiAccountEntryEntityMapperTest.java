package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.dto_projections.UserAuthorizedUbiAccountProjection;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserAuthorizedUbiAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {
    @Autowired
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void createUserUbiAccountEntry_should_map_projection_to_dto() {
        UserAuthorizedUbiAccountProjection projection = new UserAuthorizedUbiAccountProjection();
        projection.setUserId(1L);
        projection.setProfileId("ubiProfileId");
        projection.setCreditAmount(3);
        projection.setOwnedItemsIdsCount(4);
        projection.setTicket("ubiAuthTicket");
        projection.setSpaceId("ubiSpaceId");
        projection.setSessionId("ubiSessionId");
        projection.setRememberDeviceTicket("ubiRememberDeviceTicket");
        projection.setRememberMeTicket("ubiRememberMeTicket");

        UserAuthorizedUbiAccount userAuthorizedUbiAccount = ubiAccountEntryEntityMapper.createUserUbiAccountEntry(projection);

        assertEquals(1L, userAuthorizedUbiAccount.getUserId());
        assertEquals("ubiProfileId", userAuthorizedUbiAccount.getProfileId());
        assertEquals(3, userAuthorizedUbiAccount.getCreditAmount());
        assertEquals(4, userAuthorizedUbiAccount.getOwnedItemsIdsCount());
        assertEquals("ubiAuthTicket", userAuthorizedUbiAccount.getTicket());
        assertEquals("ubiSpaceId", userAuthorizedUbiAccount.getSpaceId());
        assertEquals("ubiSessionId", userAuthorizedUbiAccount.getSessionId());
        assertEquals("ubiRememberDeviceTicket", userAuthorizedUbiAccount.getRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", userAuthorizedUbiAccount.getRememberMeTicket());
    }
}