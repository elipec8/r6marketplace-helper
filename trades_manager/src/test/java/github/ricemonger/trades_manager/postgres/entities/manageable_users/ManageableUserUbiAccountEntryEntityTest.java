package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManageableUserManageableUserUbiAccountEntryEntityTest {
    @Test
    public void equals_should_return_true_if_same() {
        ManageableUserUbiAccountEntryEntity accountEntry = new ManageableUserUbiAccountEntryEntity();
        assertEquals(accountEntry, accountEntry);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        ManageableUserUbiAccountEntryEntity accountEntry1 = new ManageableUserUbiAccountEntryEntity();
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email");
        accountEntry1.setUbiAccountStats(new UbiAccountStatsEntity("profileId"));
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");

        ManageableUserUbiAccountEntryEntity accountEntry2 = new ManageableUserUbiAccountEntryEntity();
        accountEntry2.setUser(new ManageableUserEntity(1L));
        accountEntry2.setEmail("email");

        assertEquals(accountEntry1, accountEntry2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ManageableUserUbiAccountEntryEntity accountEntry = new ManageableUserUbiAccountEntryEntity();
        assertNotEquals(null, accountEntry);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ManageableUserUbiAccountEntryEntity accountEntry1 = new ManageableUserUbiAccountEntryEntity();
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email");

        ManageableUserUbiAccountEntryEntity accountEntry2 = new ManageableUserUbiAccountEntryEntity();
        accountEntry2.setUser(new ManageableUserEntity(1L));
        accountEntry2.setEmail("email");

        accountEntry1.setUser(new ManageableUserEntity(2L));
        assertNotEquals(accountEntry1, accountEntry2);
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email1");
        assertNotEquals(accountEntry1, accountEntry2);
    }

    @Test
    public void constructor_should_set_id_fields() {
        ManageableUserUbiAccountEntryEntity accountEntry = new ManageableUserUbiAccountEntryEntity();
        accountEntry.setUser(new ManageableUserEntity(1L));
        accountEntry.setEmail("email");
        accountEntry.setUbiAccountStats(new UbiAccountStatsEntity("profileId"));

        assertEquals(1L, accountEntry.getUserId_());
        assertEquals("email", accountEntry.getEmail());
        assertEquals("profileId", accountEntry.getProfileId_());
    }

    @Test
    public void getUserId_should_return_user_id() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ManageableUserUbiAccountEntryEntity accountEntry = new ManageableUserUbiAccountEntryEntity();
        accountEntry.setUser(user);
        assertEquals(1L, accountEntry.getUserId_());
    }

    @Test
    public void getProfileId_should_return_profile_id() {
        UbiAccountStatsEntity stats = new UbiAccountStatsEntity();
        stats.setUbiProfileId("profileId");
        ManageableUserUbiAccountEntryEntity accountEntry = new ManageableUserUbiAccountEntryEntity();
        accountEntry.setUbiAccountStats(stats);
        assertEquals("profileId", accountEntry.getProfileId_());
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_equal_() {
        UbiAccountStatsEntity stats = new UbiAccountStatsEntity();
        stats.setUbiProfileId("profileId");
        stats.setCreditAmount(100);
        stats.setOwnedItems(List.of());

        ManageableUserUbiAccountEntryEntity accountEntry1 = new ManageableUserUbiAccountEntryEntity();
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email@example.com");
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry1.setUbiAccountStats(stats);

        ManageableUserUbiAccountEntryEntity accountEntry2 = new ManageableUserUbiAccountEntryEntity();
        accountEntry2.setUser(new ManageableUserEntity(1L));
        accountEntry2.setEmail("email@example.com");
        accountEntry2.setUbiSessionId("sessionId");
        accountEntry2.setUbiSpaceId("spaceId");
        accountEntry2.setUbiAuthTicket("authTicket");
        accountEntry2.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry2.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry2.setUbiAccountStats(stats);

        assertTrue(accountEntry1.isFullyEqual(accountEntry2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_false_if_not_equal_() {
        UbiAccountStatsEntity stats1 = new UbiAccountStatsEntity();
        stats1.setUbiProfileId("profileId");
        stats1.setCreditAmount(100);
        stats1.setOwnedItems(List.of());

        ManageableUserUbiAccountEntryEntity accountEntry1 = new ManageableUserUbiAccountEntryEntity();
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email@example.com");
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

        ManageableUserUbiAccountEntryEntity accountEntry2 = new ManageableUserUbiAccountEntryEntity();
        accountEntry2.setUser(new ManageableUserEntity(1L));
        accountEntry2.setEmail("email@example.com");
        accountEntry2.setUbiSessionId("sessionId");
        accountEntry2.setUbiSpaceId("spaceId");
        accountEntry2.setUbiAuthTicket("authTicket");
        accountEntry2.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry2.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry2.setUbiAccountStats(stats2);

        accountEntry1.setUser(new ManageableUserEntity(2L));
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUser(new ManageableUserEntity(1L));
        accountEntry1.setEmail("email1@example.com");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setEmail("email@example.com");
        accountEntry1.setUbiSessionId("sessionId1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUbiSessionId("sessionId");
        accountEntry1.setUbiSpaceId("spaceId1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUbiSpaceId("spaceId");
        accountEntry1.setUbiAuthTicket("authTicket1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUbiAuthTicket("authTicket");
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUbiRememberDeviceTicket("rememberDeviceTicket");
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.setUbiRememberMeTicket("rememberMeTicket");
        accountEntry1.getUbiAccountStats().setUbiProfileId("profileId1");
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.getUbiAccountStats().setUbiProfileId("profileId");
        accountEntry1.getUbiAccountStats().setCreditAmount(101);
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.getUbiAccountStats().setCreditAmount(100);
        accountEntry1.getUbiAccountStats().setOwnedItems(List.of(new ItemIdEntity()));
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
        accountEntry1.getUbiAccountStats().setOwnedItems(null);
        assertFalse(accountEntry1.isFullyEqual(accountEntry2));
    }
}