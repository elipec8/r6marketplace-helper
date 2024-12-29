package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ManageableUserUbiAccountEntryEntityIdTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setUbiAccountEntry(null);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user1, "email@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user1, "email@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_emails() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user, "email1@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setTradeByFiltersManagers(null);

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setTradeByFiltersManagers(List.of());

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user1, "email@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user1, "email@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_emails() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user, "email1@example.com");
        ManageableUserUbiAccountEntryEntityId id2 = new ManageableUserUbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user, "email@example.com");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ManageableUserUbiAccountEntryEntityId id1 = new ManageableUserUbiAccountEntryEntityId(user, "email@example.com");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}