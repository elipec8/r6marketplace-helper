package github.ricemonger.utilspostgresschema.ids.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemDaySalesUbiStatsEntityIdTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setAssetUrl("1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setAssetUrl("2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_items_null_dates() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        LocalDate date = null;

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item1, date);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items_null_dates() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        LocalDate date = null;

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_dates() {
        ItemEntity item = new ItemEntity();
        item.setItemId("item1");

        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item, date1);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item, date2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        item1.setAssetUrl("1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item1");
        item2.setAssetUrl("2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_dates() {
        ItemEntity item = new ItemEntity();
        item.setItemId("item");
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item, date1);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item, date2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }

}