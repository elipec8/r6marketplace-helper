package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemResaleLockEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id_fields() {
        ItemResaleLockEntity entity1 = new ItemResaleLockEntity();
        entity1.setUbiAccount(new UbiAccountStatsEntity());
        entity1.getUbiAccount().setUbiProfileId("ubiProfileId");
        entity1.setItem(new ItemIdEntity("itemId"));

        ItemResaleLockEntity entity2 = new ItemResaleLockEntity();
        entity2.setUbiAccount(new UbiAccountStatsEntity());
        entity2.getUbiAccount().setUbiProfileId("ubiProfileId");
        entity2.setItem(new ItemIdEntity("itemId"));

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        assertFalse(entity.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_for_different_ids() {
        ItemResaleLockEntity entity1 = new ItemResaleLockEntity();
        entity1.setUbiAccount(new UbiAccountStatsEntity());
        entity1.getUbiAccount().setUbiProfileId("ubiProfileId");
        entity1.setItem(new ItemIdEntity("itemId"));

        ItemResaleLockEntity entity2 = new ItemResaleLockEntity();
        entity2.setUbiAccount(new UbiAccountStatsEntity());
        entity2.getUbiAccount().setUbiProfileId("ubiProfileId");
        entity2.setItem(new ItemIdEntity("itemId"));

        entity1.setItem(new ItemIdEntity("itemId1"));
        assertFalse(entity1.isEqual(entity2));
        entity1.setItem(new ItemIdEntity("itemId"));
        entity1.setUbiAccount(new UbiAccountStatsEntity());
        entity1.getUbiAccount().setUbiProfileId("ubiProfileId1");
        assertFalse(entity1.isEqual(entity2));
    }
}