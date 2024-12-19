package github.ricemonger.marketplace.databases.postgres.entities.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemFilterEntityIdTest {

    @Test
    public void getUserId_should_return_user_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemFilterEntityId id = new ItemFilterEntityId(user, "filterName");

        assertEquals(1L, id.getUserId());
    }

    @Test
    public void constructor_should_set_id_fields() {
        ItemFilterEntityId id = new ItemFilterEntityId(1L, "filterName");

        assertEquals(1L, id.getUserId());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowNameFlag(true);
        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowNameFlag(false);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName1");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user, "filterName2");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowNameFlag(true);
        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowNameFlag(false);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }

    @Test
    public void equals_should_return_false_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName1");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user, "filterName2");

        assertNotEquals(id1, id2);
    }

}