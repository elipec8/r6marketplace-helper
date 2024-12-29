package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemResaleLockEntityIdTest {

    @Test
    public void hashCode_should_return_equals_for_different_fields_equal_ids() {
        ItemResaleLockEntityId itemResaleLockEntityId1 = new ItemResaleLockEntityId();
        itemResaleLockEntityId1.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId1.getUbiAccount().setCreditAmount(1);
        itemResaleLockEntityId1.getUbiAccount().setSoldIn24h(2);
        itemResaleLockEntityId1.getUbiAccount().setSoldIn24h(3);
        itemResaleLockEntityId1.setItem(new ItemIdEntity("itemId"));

        ItemResaleLockEntityId itemResaleLockEntityId2 = new ItemResaleLockEntityId();
        itemResaleLockEntityId2.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId2.setItem(new ItemIdEntity("itemId"));

        assertEquals(itemResaleLockEntityId1.hashCode(), itemResaleLockEntityId2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemResaleLockEntityId itemResaleLockEntityId = new ItemResaleLockEntityId();
        assertEquals(itemResaleLockEntityId, itemResaleLockEntityId);
    }

    @Test
    public void equals_should_return_true_if_equal_id_fields() {
        ItemResaleLockEntityId itemResaleLockEntityId1 = new ItemResaleLockEntityId();
        itemResaleLockEntityId1.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId1.getUbiAccount().setCreditAmount(1);
        itemResaleLockEntityId1.getUbiAccount().setSoldIn24h(2);
        itemResaleLockEntityId1.getUbiAccount().setSoldIn24h(3);
        itemResaleLockEntityId1.setItem(new ItemIdEntity("itemId"));

        ItemResaleLockEntityId itemResaleLockEntityId2 = new ItemResaleLockEntityId();
        itemResaleLockEntityId2.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId2.setItem(new ItemIdEntity("itemId"));

        assertEquals(itemResaleLockEntityId1, itemResaleLockEntityId2);
    }

    @Test
    public void equals_should_return_false_for_null(){
        ItemResaleLockEntityId itemResaleLockEntityId = new ItemResaleLockEntityId();
        assertNotEquals(null, itemResaleLockEntityId);
    }

    @Test
    public void equals_should_return_false_for_different_id_fields(){
        ItemResaleLockEntityId itemResaleLockEntityId1 = new ItemResaleLockEntityId();
        itemResaleLockEntityId1.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId1.setItem(new ItemIdEntity("itemId"));

        ItemResaleLockEntityId itemResaleLockEntityId2 = new ItemResaleLockEntityId();
        itemResaleLockEntityId2.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId2.setItem(new ItemIdEntity("itemId"));

        itemResaleLockEntityId1.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId1"));
        assertNotEquals(itemResaleLockEntityId1, itemResaleLockEntityId2);
        itemResaleLockEntityId1.setUbiAccount(new UbiAccountStatsEntity("ubiProfileId"));
        itemResaleLockEntityId1.setItem(new ItemIdEntity("itemId1"));
        assertNotEquals(itemResaleLockEntityId1, itemResaleLockEntityId2);
    }
}