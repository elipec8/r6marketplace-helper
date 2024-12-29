package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountEntryEntityIdTest {

    @Test
    public void hashCode_should_return_true_for_equal_ids(){
        UserUbiAccountEntryEntity user1 = new UserUbiAccountEntryEntity(1L);
        user1.setUbiAccountEntry(new UbiAccountEntryEntity());
        user1.getUbiAccountEntry().setUbiAuthTicket("ubiAuthTicket");
        user1.getUbiAccountEntry().setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        user1.getUbiAccountEntry().setUbiRememberMeTicket("ubiRememberMeTicket");
        user1.getUbiAccountEntry().setUbiSessionId("ubiSessionId");
        user1.getUbiAccountEntry().setUbiSpaceId("ubiSpaceId");

        UserUbiAccountEntryEntity user2 = new UserUbiAccountEntryEntity(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email");
        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_ids(){
        UserUbiAccountEntryEntity user1 = new UserUbiAccountEntryEntity(1L);
        user1.setUbiAccountEntry(new UbiAccountEntryEntity());
        user1.getUbiAccountEntry().setUbiAuthTicket("ubiAuthTicket");
        user1.getUbiAccountEntry().setUbiRememberDeviceTicket("ubiRememberDeviceTicket");
        user1.getUbiAccountEntry().setUbiRememberMeTicket("ubiRememberMeTicket");
        user1.getUbiAccountEntry().setUbiSessionId("ubiSessionId");
        user1.getUbiAccountEntry().setUbiSpaceId("ubiSpaceId");

        UserUbiAccountEntryEntity user2 = new UserUbiAccountEntryEntity(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email");
        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_ids(){
        UserUbiAccountEntryEntity user1 = new UserUbiAccountEntryEntity(1L);

        UserUbiAccountEntryEntity user2 = new UserUbiAccountEntryEntity(2L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email");

        assertNotEquals(id1, id2);
    }
}