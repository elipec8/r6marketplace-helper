package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemResaleLockEntityIdTest {
    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        ubiAccount1.setBoughtIn24h(20);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setName("name1");

        UbiAccountStatsEntity ubiAccount2 = new UbiAccountStatsEntity();
        ubiAccount2.setUbiProfileId("profile1");
        ubiAccount2.setBoughtIn24h(10);
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setName("name2");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount2, item2);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_profiles() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        UbiAccountStatsEntity ubiAccount2 = new UbiAccountStatsEntity();
        ubiAccount2.setUbiProfileId("profile2");
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount2, item1);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount1, item2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        ubiAccount1.setBoughtIn24h(20);
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setName("name1");

        UbiAccountStatsEntity ubiAccount2 = new UbiAccountStatsEntity();
        ubiAccount2.setUbiProfileId("profile1");
        ubiAccount2.setBoughtIn24h(10);
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setName("name2");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount2, item2);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_profiles() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        UbiAccountStatsEntity ubiAccount2 = new UbiAccountStatsEntity();
        ubiAccount2.setUbiProfileId("profile2");
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount2, item1);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items() {
        UbiAccountStatsEntity ubiAccount1 = new UbiAccountStatsEntity();
        ubiAccount1.setUbiProfileId("profile1");
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount1, item1);
        ItemResaleLockEntityId id2 = new ItemResaleLockEntityId(ubiAccount1, item2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount, item);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        UbiAccountStatsEntity ubiAccount = new UbiAccountStatsEntity();
        ubiAccount.setUbiProfileId("profile1");
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        ItemResaleLockEntityId id1 = new ItemResaleLockEntityId(ubiAccount, item);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }
}