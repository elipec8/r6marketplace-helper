package github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.user;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UbiAccountEntryEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UbiAccountStatsIdCreditAmountEntity;
import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import github.ricemonger.ubi_users_stats_fetcher.services.DTOs.UserUbiAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UbiAccountEntryEntityMapperTest {
    @Autowired
    private UbiAccountEntryEntityMapper ubiAccountEntryEntityMapper;

    @Test
    public void createUserUbiAccountEntry_should_map_entity_to_dto() {
        UserUbiAccountEntryEntity entity = new UserUbiAccountEntryEntity();
        entity.setId(1L);
        entity.setUbiAccountEntry(new UbiAccountEntryEntity());
        entity.getUbiAccountEntry().setUbiAccountStats(new UbiAccountStatsIdCreditAmountEntity());
        entity.getUbiAccountEntry().getUbiAccountStats().setUbiProfileId("ubiProfileId");
        entity.getUbiAccountEntry().getUbiAccountStats().setCreditAmount(3);
        entity.getUbiAccountEntry().setUbiAuthTicket("ubiAuthTicket");
        entity.getUbiAccountEntry().setUbiSpaceId("ubiSpaceId");
        entity.getUbiAccountEntry().setUbiSessionId("ubiSessionId");
        entity.getUbiAccountEntry().setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        entity.getUbiAccountEntry().setUbiRememberMeTicket("ubiRememberMeTicket");

        UserUbiAccount userUbiAccount = ubiAccountEntryEntityMapper.createUserUbiAccountEntry(entity);

        assertEquals(1L, userUbiAccount.getUserId());
        assertEquals("ubiProfileId", userUbiAccount.getProfileId());
        assertEquals(3, userUbiAccount.getCreditAmount());
        assertEquals("ubiAuthTicket", userUbiAccount.getTicket());
        assertEquals("ubiSpaceId", userUbiAccount.getSpaceId());
        assertEquals("ubiSessionId", userUbiAccount.getSessionId());
        assertEquals("ubiRememberDeviceTicket", userUbiAccount.getRememberDeviceTicket());
        assertEquals("ubiRememberMeTicket", userUbiAccount.getRememberMeTicket());
    }
}