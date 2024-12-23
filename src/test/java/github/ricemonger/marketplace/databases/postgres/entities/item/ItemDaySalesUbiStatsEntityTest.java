package github.ricemonger.marketplace.databases.postgres.entities.item;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemDaySalesUbiStatsEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id_fields() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(3);
        entity1.setItemsCount(4);

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));
        entity2.setLowestPrice(5);
        entity2.setAveragePrice(6);
        entity2.setHighestPrice(7);
        entity2.setItemsCount(8);

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        assertFalse(entity.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_for_different_ids() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));

        entity1.setItem(new ItemEntity("itemId1"));
        assertFalse(entity1.isEqual(entity2));
        entity1.setItem(new ItemEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 2));
        assertFalse(entity1.isEqual(entity2));
    }

    @Test
    public void getItemId_should_return_item_itemId() {
        ItemEntity item = new ItemEntity();
        item.setItemId("itemId");
        ItemDaySalesUbiStatsEntity itemDaySalesUbiStatsEntity = new ItemDaySalesUbiStatsEntity();
        itemDaySalesUbiStatsEntity.setItem(item);
        assertEquals("itemId", itemDaySalesUbiStatsEntity.getItemId_());
    }

    @Test
    public void isFullyEqual_should_return_true_if_same() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void is_FullyEqual_should_return_true_if_equal() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(3);
        entity1.setItemsCount(4);
        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));
        entity2.setLowestPrice(1);
        entity2.setAveragePrice(2);
        entity2.setHighestPrice(3);
        entity2.setItemsCount(4);

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemEntity("itemId1"));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(3);
        entity1.setItemsCount(4);
        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));
        entity2.setLowestPrice(1);
        entity2.setAveragePrice(2);
        entity2.setHighestPrice(3);
        entity2.setItemsCount(4);

        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setItem(new ItemEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 2));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(2);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(3);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(4);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setHighestPrice(3);
        entity1.setItemsCount(5);
        assertFalse(entity1.isFullyEqual(entity2));
    }
}