package github.ricemonger.utilspostgresschema.full_entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiAccountEntryEntityTest {
    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(userEntity);
        entity1.setEmail("email");
        entity1.setEncodedPassword("password");
        entity1.setUbiSessionId("session");
        entity1.setUbiSpaceId("space");
        entity1.setUbiAuthTicket("ticket");
        entity1.setUbiRememberDeviceTicket("device");
        entity1.setUbiRememberMeTicket("me");
        entity1.setUbiAccountStats(new UbiAccountStatsEntity());
        entity1.getUbiAccountStats().setUbiProfileId("profile");

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserEntity());
        entity2.getUser().setId(1L);
        entity2.setEmail("email");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_same() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        assertEquals(entity1, entity1);
    }

    @Test
    public void equals_should_return_true_for_same_ids() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(userEntity);
        entity1.setEmail("email");
        entity1.setEncodedPassword("password");
        entity1.setUbiSessionId("session");
        entity1.setUbiSpaceId("space");
        entity1.setUbiAuthTicket("ticket");
        entity1.setUbiRememberDeviceTicket("device");
        entity1.setUbiRememberMeTicket("me");
        entity1.setUbiAccountStats(new UbiAccountStatsEntity());
        entity1.getUbiAccountStats().setUbiProfileId("profile");

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserEntity());
        entity2.getUser().setId(1L);
        entity2.setEmail("email");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        entity1.setUser(userEntity);
        entity1.setEmail("email");

        UbiAccountEntryEntity entity2 = new UbiAccountEntryEntity();
        entity2.setUser(new UserEntity());
        entity2.getUser().setId(1L);
        entity2.setEmail("email");

        entity1.getUser().setId(2L);
        assertNotEquals(entity1, entity2);
        entity1.getUser().setId(1L);
        entity1.setEmail("email2");
        assertNotEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiAccountEntryEntity entity1 = new UbiAccountEntryEntity();
        assertNotEquals(null, entity1);
    }
}