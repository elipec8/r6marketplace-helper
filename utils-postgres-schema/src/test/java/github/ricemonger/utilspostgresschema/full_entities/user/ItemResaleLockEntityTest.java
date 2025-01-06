package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemResaleLockEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        ItemResaleLockEntity itemResaleLockEntity1 = new ItemResaleLockEntity();
        itemResaleLockEntity1.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity1.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity1.setItem(new ItemEntity());
        itemResaleLockEntity1.getItem().setItemId("itemId");
        itemResaleLockEntity1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ItemResaleLockEntity itemResaleLockEntity2 = new ItemResaleLockEntity();
        itemResaleLockEntity2.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity2.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity2.setItem(new ItemEntity());
        itemResaleLockEntity2.getItem().setItemId("itemId");

        assertEquals(itemResaleLockEntity1.hashCode(), itemResaleLockEntity2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_same() {
        ItemResaleLockEntity entity = new ItemResaleLockEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_for_equal_ids() {
        ItemResaleLockEntity itemResaleLockEntity1 = new ItemResaleLockEntity();
        itemResaleLockEntity1.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity1.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity1.setItem(new ItemEntity());
        itemResaleLockEntity1.getItem().setItemId("itemId");
        itemResaleLockEntity1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ItemResaleLockEntity itemResaleLockEntity2 = new ItemResaleLockEntity();
        itemResaleLockEntity2.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity2.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity2.setItem(new ItemEntity());
        itemResaleLockEntity2.getItem().setItemId("itemId");

        assertEquals(itemResaleLockEntity1, itemResaleLockEntity2);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        ItemResaleLockEntity itemResaleLockEntity1 = new ItemResaleLockEntity();
        itemResaleLockEntity1.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity1.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity1.setItem(new ItemEntity());
        itemResaleLockEntity1.getItem().setItemId("itemId");

        ItemResaleLockEntity itemResaleLockEntity2 = new ItemResaleLockEntity();
        itemResaleLockEntity2.setUbiAccount(new UbiAccountStatsEntity());
        itemResaleLockEntity2.getUbiAccount().setUbiProfileId("ubiProfileId");
        itemResaleLockEntity2.setItem(new ItemEntity());
        itemResaleLockEntity2.getItem().setItemId("itemId");

        itemResaleLockEntity1.getItem().setItemId("itemId1");
        assertNotEquals(itemResaleLockEntity1, itemResaleLockEntity2);
        itemResaleLockEntity1.getItem().setItemId("itemId");
        itemResaleLockEntity1.getUbiAccount().setUbiProfileId("ubiProfileId1");
        assertNotEquals(itemResaleLockEntity1, itemResaleLockEntity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemResaleLockEntity itemResaleLockEntity = new ItemResaleLockEntity();
        assertNotEquals(null, itemResaleLockEntity);
    }
}