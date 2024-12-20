package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountEntryEntityTest {

    @Test
    public void constructor_should_set_id_fields() {
        UbiAccountEntryEntity accountEntry = new UbiAccountEntryEntity(1L, "email", "profileId");

        assertEquals(1L, accountEntry.getUserId());
        assertEquals("email", accountEntry.getEmail());
        assertEquals("profileId", accountEntry.getProfileId());
    }

    @Test
    public void getUserId_should_return_user_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        UbiAccountEntryEntity accountEntry = new UbiAccountEntryEntity();
        accountEntry.setUser(user);
        assertEquals(1L, accountEntry.getUserId());
    }

    @Test
    public void getProfileId_should_return_profile_id() {
        UbiAccountStatsEntity stats = new UbiAccountStatsEntity();
        stats.setUbiProfileId("profileId");
        UbiAccountEntryEntity accountEntry = new UbiAccountEntryEntity();
        accountEntry.setUbiAccountStats(stats);
        assertEquals("profileId", accountEntry.getProfileId());
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_except_user() {
        UbiAccountStatsEntity stats = new UbiAccountStatsEntity();
        stats.setUbiProfileId("profileId");
        stats.setCreditAmount(100);
        stats.setOwnedItems(List.of());

        UbiAccountEntryEntity accountEntry1 = new UbiAccountEntryEntity();
        accountEntry1.setUser(new UserEntity(1L));
        accountEntry1.setEmail("email@example.com");
        accountEntry1.setEncodedPassword("encodedPassword");
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry1.setUbiAccountStats(stats);

        UbiAccountEntryEntity accountEntry2 = new UbiAccountEntryEntity();
        accountEntry2.setUser(new UserEntity(1L));
        accountEntry2.setEmail("email@example.com");
        accountEntry2.setEncodedPassword("encodedPassword");
        accountEntry2.setUbiSessionId("sessionId");
        accountEntry2.setUbiSpaceId("spaceId");
        accountEntry2.setUbiAuthTicket("authTicket");
        accountEntry2.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry2.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry2.setUbiAccountStats(stats);

        assertTrue(accountEntry1.isFullyEqualExceptUser(accountEntry2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_except_user() {
        UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity();
        stats1.setUbiProfileId("profileId");
        stats1.setCreditAmount(100);
        stats1.setOwnedItems(List.of());

        UbiAccountEntryEntity accountEntry1 = new UbiAccountEntryEntity();
        accountEntry1.setUser(new UserEntity(1L));
        accountEntry1.setEmail("email@example.com");
        accountEntry1.setEncodedPassword("encodedPassword");
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry1.setUbiAccountStats(stats1);

        UbiAccountStatsEntity stats2 = new UbiAccountStatsEntity();
        stats2.setUbiProfileId("profileId");
        stats2.setCreditAmount(100);
        stats2.setOwnedItems(List.of());

        UbiAccountEntryEntity accountEntry2 = new UbiAccountEntryEntity();
        accountEntry2.setUser(new UserEntity(1L));
        accountEntry2.setEmail("email@example.com");
        accountEntry2.setEncodedPassword("encodedPassword");
        accountEntry2.setUbiSessionId("sessionId");
        accountEntry2.setUbiSpaceId("spaceId");
        accountEntry2.setUbiAuthTicket("authTicket");
        accountEntry2.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry2.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry2.setUbiAccountStats(stats2);

        accountEntry1.setUser(new UserEntity(2L));
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUser(new UserEntity(1L));
        accountEntry1.setEmail("email1@example.com");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setEmail("email@example.com");
        accountEntry1.setEncodedPassword("encodedPassword1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setEncodedPassword("encodedPassword");
        accountEntry1.setUbiSessionId("sessionId1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry1.getUbiAccountStats().setUbiProfileId("profileId1");
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.getUbiAccountStats().setUbiProfileId("profileId");
        accountEntry1.getUbiAccountStats().setCreditAmount(101);
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.getUbiAccountStats().setCreditAmount(100);
        accountEntry1.getUbiAccountStats().setOwnedItems(List.of(new ItemEntity()));
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
        accountEntry1.getUbiAccountStats().setOwnedItems(null);
        assertFalse(accountEntry1.isFullyEqualExceptUser(accountEntry2));
    }
}