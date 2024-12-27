package github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountEntryEntityIdTest {
    @Test
    public void getUserId_should_return_user_id() {
        UbiAccountEntryEntityId id = new UbiAccountEntryEntityId(new UserIdEntity(1L), "email");

        assertEquals(1L, id.getUserId_());
    }

    @Test
    public void constructor_should_set_user_and_email() {
        UbiAccountEntryEntityId id = new UbiAccountEntryEntityId(1L, "email");

        assertEquals(1L, id.getUserId_());
        assertEquals("email", id.getEmail());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserIdEntity user1 = new UserIdEntity();
        user1.setId(1L);

        UserIdEntity user2 = new UserIdEntity();
        user2.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserIdEntity user1 = new UserIdEntity();
        user1.setId(1L);
        UserIdEntity user2 = new UserIdEntity();
        user2.setId(2L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_emails() {
        UserIdEntity user = new UserIdEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email1@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UserIdEntity user1 = new UserIdEntity();
        user1.setId(1L);

        UserIdEntity user2 = new UserIdEntity();
        user2.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_objects() {
        UserIdEntity user1 = new UserIdEntity();
        user1.setId(1L);
        UserIdEntity user2 = new UserIdEntity();
        user2.setId(2L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user1, "email@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user2, "email@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_emails() {
        UserIdEntity user = new UserIdEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email1@example.com");
        UbiAccountEntryEntityId id2 = new UbiAccountEntryEntityId(user, "email2@example.com");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UserIdEntity user = new UserIdEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email@example.com");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserIdEntity user = new UserIdEntity();
        user.setId(1L);

        UbiAccountEntryEntityId id1 = new UbiAccountEntryEntityId(user, "email@example.com");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}