package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service.ItemFilterUserIdEntityId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemFilterEntityIdTest {

    @Test
    public void getUserId_should_return_user_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemFilterUserIdEntityId id = new ItemFilterUserIdEntityId(user, "filterName");

        assertEquals(1L, id.getUserId_());
    }

    @Test
    public void constructor_should_set_id_fields() {
        ItemFilterUserIdEntityId id = new ItemFilterUserIdEntityId(1L, "filterName");

        assertEquals(1L, id.getUserId_());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowNameFlag(true);
        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowNameFlag(false);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user1, "filterName");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user2, "filterName");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user1, "filterName");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user2, "filterName");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user, "filterName1");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user, "filterName2");

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

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user1, "filterName");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user2, "filterName");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user1, "filterName");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user2, "filterName");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user, "filterName");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user, "filterName");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }

    @Test
    public void equals_should_return_false_for_different_names() {
        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterUserIdEntityId id1 = new ItemFilterUserIdEntityId(user, "filterName1");
        ItemFilterUserIdEntityId id2 = new ItemFilterUserIdEntityId(user, "filterName2");

        assertNotEquals(id1, id2);
    }

}