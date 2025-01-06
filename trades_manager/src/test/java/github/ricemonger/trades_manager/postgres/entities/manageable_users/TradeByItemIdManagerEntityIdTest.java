package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TradeByItemIdManagerEntityIdTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(null);
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item2);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user, item2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setTradeByFiltersManagers(null);
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");

        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setTradeByFiltersManagers(List.of());
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item2);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user1, item);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user2, item);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item2");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item1);
        TradeByItemIdManagerEntityId id2 = new TradeByItemIdManagerEntityId(user, item2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item1");

        TradeByItemIdManagerEntityId id1 = new TradeByItemIdManagerEntityId(user, item);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}