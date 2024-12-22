package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TradeByItemIdManagerEntityIdTest {

    @Test
    public void getUserId_should_return_user_idField() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id = new TradeByItemIdManagerEntityId(user, item);

        assertEquals(1L, id.getUserId_());
    }

    @Test
    public void getItemId_should_return_item_id() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id = new TradeByItemIdManagerEntityId(user, item);

        assertEquals("item1", id.getItemId_());
    }

    @Test
    public void constructor_should_create_object_with_id_fields() {
        TradeByItemIdManagerEntityId id = new TradeByItemIdManagerEntityId(1L, "item1");

        assertEquals(1L, id.getUserId_());
        assertEquals("item1", id.getItemId_());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setName("name1");

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setName("name2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item2);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user, item2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setItemShowItemTypeFlag(true);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setName("name1");

        UserEntity user2 = new UserEntity();
        user2.setId(1L);
        user2.setItemShowItemTypeFlag(false);
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setName("name2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item2);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user, item2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}