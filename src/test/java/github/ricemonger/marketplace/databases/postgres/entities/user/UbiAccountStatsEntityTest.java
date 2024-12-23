package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UbiAccountStatsEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setOwnedItems(List.of(new ItemEntity("itemId")));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId");

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertFalse(entity.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_not_equal_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId2");

        assertFalse(entity1.isEqual(entity2));
    }

    @Test
    public void isFullyEqualExceptUser_should_return_true_if_same() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setOwnedItems(List.of());

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setOwnedItems(List.of());

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setOwnedItems(List.of());

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setOwnedItems(List.of());

        entity1.setUbiProfileId("ubiProfileId2");
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(2);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCreditAmount(1);
        entity1.setOwnedItems(List.of(new ItemEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(null);
        assertFalse(entity1.isFullyEqual(entity2));
    }
}