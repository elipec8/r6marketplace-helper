package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserUbiAccountEntryEntityTest {

    @Test
    public void getProfileId_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getProfileId_());
    }

    @Test
    public void getProfileId_should_return_ubiAccountEntry_profileId() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(new UbiAccountStatsIdCreditAmountEntity("ubiProfileId", 101));

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiProfileId", userUbiAccountEntryEntity.getProfileId_());
    }

    @Test
    public void getCreditAmount_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getCreditAmount_());
    }

    @Test
    public void getCreditAmount_should_return_ubiAccountEntry_creditAmount() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAccountStats(new UbiAccountStatsIdCreditAmountEntity("ubiProfileId", 101));

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals(101, userUbiAccountEntryEntity.getCreditAmount_());
    }

    @Test
    public void getUbiAuthTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getUbiAuthTicket_());
    }

    @Test
    public void getUbiAuthTicket_should_return_ubiAccountEntry_ubiAuthTicket() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiAuthTicket("ubiAuthTicket");

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiAuthTicket", userUbiAccountEntryEntity.getUbiAuthTicket_());
    }

    @Test
    public void getUbiSpaceId_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getUbiSpaceId_());
    }

    @Test
    public void getUbiSpaceId_should_return_ubiAccountEntry_ubiSpaceId() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiSpaceId("ubiSpaceId");

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiSpaceId", userUbiAccountEntryEntity.getUbiSpaceId_());
    }

    @Test
    public void getUbiSessionId_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getUbiSessionId_());
    }

    @Test
    public void getUbiSessionId_should_return_ubiAccountEntry_ubiSessionId() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiSessionId("ubiSessionId");

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiSessionId", userUbiAccountEntryEntity.getUbiSessionId_());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getUbiRememberDeviceTicket_());
    }

    @Test
    public void getUbiRememberDeviceTicket_should_return_ubiAccountEntry_ubiRememberDeviceTicket() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiRememberDeviceTicket("ubiRememberDeviceTicket");

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiRememberDeviceTicket", userUbiAccountEntryEntity.getUbiRememberDeviceTicket_());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_null_if_ubiAccountEntry_is_null() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        assertNull(userUbiAccountEntryEntity.getUbiRememberMeTicket_());
    }

    @Test
    public void getUbiRememberMeTicket_should_return_ubiAccountEntry_ubiRememberMeTicket() {
        UserUbiAccountEntryEntity userUbiAccountEntryEntity = new UserUbiAccountEntryEntity(1L);
        UbiAccountEntryEntity ubiAccountEntryEntity = new UbiAccountEntryEntity();
        ubiAccountEntryEntity.setUbiRememberMeTicket("ubiRememberMeTicket");

        userUbiAccountEntryEntity.setUbiAccountEntry(ubiAccountEntryEntity);
        assertEquals("ubiRememberMeTicket", userUbiAccountEntryEntity.getUbiRememberMeTicket_());
    }
}