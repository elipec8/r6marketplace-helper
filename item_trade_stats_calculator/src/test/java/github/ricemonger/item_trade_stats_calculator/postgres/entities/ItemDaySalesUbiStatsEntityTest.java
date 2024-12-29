package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemDaySalesUbiStatsEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemIdEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(3);
        entity1.setItemsCount(4);

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemIdEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id_fields() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemIdEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));
        entity1.setLowestPrice(1);
        entity1.setAveragePrice(2);
        entity1.setHighestPrice(3);
        entity1.setItemsCount(4);

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemIdEntity("itemId"));
        entity2.setDate(LocalDate.of(2021, 1, 1));
        entity2.setLowestPrice(5);
        entity2.setAveragePrice(6);
        entity2.setHighestPrice(7);
        entity2.setItemsCount(8);

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_id_item() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemIdEntity("itemId"));
        entity1.setDate(LocalDate.of(2021, 1, 1));

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemIdEntity("itemId1"));
        entity2.setDate(LocalDate.of(2021, 1, 1));

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_different_id_date() {
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(new ItemIdEntity("itemId1"));
        entity1.setDate(LocalDate.of(2021, 1, 1));

        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(new ItemIdEntity("itemId1"));
        entity2.setDate(LocalDate.of(2022, 1, 1));

        assertNotEquals(entity1, entity2);
    }
}