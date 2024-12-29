package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities;

import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");
        entity1.setEncodedPassword("encodedPassword");
        entity1.setUbiSessionId("ubiSessionId");
        entity1.setUbiSpaceId("ubiSpaceId");
        entity1.setUbiAuthTicket("ubiAuthTicket");
        entity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        entity1.setUbiRememberMeTicket("ubiRememberMeTicket");
        entity1.setUbiAccountStats(new UbiAccountStatsIdEntity("ubiProfileId"));

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");
        entity1.setEncodedPassword("encodedPassword");
        entity1.setUbiSessionId("ubiSessionId");
        entity1.setUbiSpaceId("ubiSpaceId");
        entity1.setUbiAuthTicket("ubiAuthTicket");
        entity1.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        entity1.setUbiRememberMeTicket("ubiRememberMeTicket");
        entity1.setUbiAccountStats(new UbiAccountStatsIdEntity("ubiProfileId"));

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UbiAccountEntryEntity entity = new UbiAccountEntryEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_id_user() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserIdEntity(2L));
        entity2.setEmail("email");

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_different_id_email() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(new UserIdEntity(1L));
        entity1.setEmail("email");

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserIdEntity(1L));
        entity2.setEmail("email1");

        assertNotEquals(entity1, entity2);
    }
}