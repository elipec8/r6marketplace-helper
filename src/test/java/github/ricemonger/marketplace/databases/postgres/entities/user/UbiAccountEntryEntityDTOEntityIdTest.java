package github.ricemonger.marketplace.databases.postgres.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiAccountEntryEntityDTOEntityIdTest {
    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_emails() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email1@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_emails() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email1@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email@example.com");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email@example.com");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}