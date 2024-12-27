package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemDaySalesUbiStatsEntityIdTest {

    @Test
    public void getItemId_should_return_item_id() {
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("itemId");

        ItemDaySalesUbiStatsEntityId id = new ItemDaySalesUbiStatsEntityId(item, LocalDate.of(2023, 1, 1));

        assertEquals("itemId", id.getItemId_());
    }

    @Test
    public void constructor_should_set_id_fields() {
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id = new ItemDaySalesUbiStatsEntityId("itemId", date);

        assertEquals("itemId", id.getItemId_());
        assertEquals(date, id.getDate());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_objects() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_same_hash_for_equal_items_null_dates() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        LocalDate date = null;

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item1, date);

        assertEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_items_null_dates() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item2");
        LocalDate date = null;

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void hashCode_should_return_different_hash_for_different_dates() {
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item1");

        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item, date1);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item, date2);

        assertNotEquals(id1.hashCode(), id2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_equal_objects() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_items() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        ItemIdEntity item2 = new ItemIdEntity();
        item2.setItemId("item2");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item2, date);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_different_dates() {
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("item");
        LocalDate date1 = LocalDate.of(2023, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item, date1);
        ItemDaySalesUbiStatsEntityId id2 = new ItemDaySalesUbiStatsEntityId(item, date2);

        assertNotEquals(id1, id2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);

        assertNotEquals(null, id1);
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        ItemIdEntity item1 = new ItemIdEntity();
        item1.setItemId("item1");
        LocalDate date = LocalDate.of(2023, 1, 1);

        ItemDaySalesUbiStatsEntityId id1 = new ItemDaySalesUbiStatsEntityId(item1, date);
        Object obj = new Object();

        assertNotEquals(id1, obj);
    }

}