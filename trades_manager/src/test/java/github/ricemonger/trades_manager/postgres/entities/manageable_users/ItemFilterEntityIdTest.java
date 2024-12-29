package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemFilterEntityIdTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setUbiAccountEntry(new ManageableUserUbiAccountEntryEntity());
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setUbiAccountEntry(null);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_users() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_names() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName1");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user, "filterName2");

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        user1.setTradeByFiltersManagers(null);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(1L);
        user2.setTradeByFiltersManagers(List.of(new TradeByFiltersManagerEntity()));

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_users() {
        ManageableUserEntity user1 = new ManageableUserEntity();
        user1.setId(1L);
        ManageableUserEntity user2 = new ManageableUserEntity();
        user2.setId(2L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user1, "filterName");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user2, "filterName");

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName");

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName");
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }

    @Test
    public void equals_should_return_false_for_different_names() {
        ManageableUserEntity user = new ManageableUserEntity();
        user.setId(1L);

        ItemFilterEntityId id1 = new ItemFilterEntityId(user, "filterName1");
        ItemFilterEntityId id2 = new ItemFilterEntityId(user, "filterName2");

        assertNotEquals(id1, id2);
    }
}