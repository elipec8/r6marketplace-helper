package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UbiAccountEntryEntityTest {

    @Test
    public void getUbiProfileId_should_return_null_when_ubiAccountStats_is_null() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(null);

        assertNull(entity.getUbiProfileId_());
    }

    @Test
    public void getUbiProfileId_should_return_ubiProfileId() {
        UbiAccountStatsIdEntity stats = new UbiAccountStatsIdEntity();
        stats.setUbiProfileId("ubiProfileId");

        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        entity.setUbiAccountStats(stats);

        assertEquals("ubiProfileId", entity.getUbiProfileId_());
    }

    @Test
    public void setAuthorizationDTOFields_should_set_fields_expect_ubiProfileId() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();

        entity.setAuthorizationDTOFields(new AuthorizationDTO("ticket", "profileId", "spaceId", "sessionId", "rememberDeviceTicket",
                "rememberMeTicket"));

        assertEquals("sessionId", entity.getUbiSessionId());
        assertEquals("spaceId", entity.getUbiSpaceId());
        assertEquals("ticket", entity.getUbiAuthTicket());
        assertEquals("rememberDeviceTicket", entity.getUbiRememberDeviceTicket());
        assertEquals("rememberMeTicket", entity.getUbiRememberMeTicket());
        assertNull(entity.getUbiProfileId_());
    }
}