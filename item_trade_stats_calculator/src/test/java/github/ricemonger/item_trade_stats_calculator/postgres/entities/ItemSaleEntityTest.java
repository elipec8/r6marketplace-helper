package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemSaleEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_ids() {
        ItemSaleEntity itemSaleEntity1 = new ItemSaleEntity();
        itemSaleEntity1.setItem(new ItemIdEntity("itemId"));
        itemSaleEntity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        itemSaleEntity1.setPrice(1);

        ItemSaleEntity itemSaleEntity2 = new ItemSaleEntity();
        itemSaleEntity2.setItem(new ItemIdEntity("itemId"));
        itemSaleEntity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        itemSaleEntity2.setPrice(2);

        assertEquals(itemSaleEntity1.hashCode(), itemSaleEntity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemSaleEntity itemSaleEntity = new ItemSaleEntity();
        assertEquals(itemSaleEntity, itemSaleEntity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemSaleEntity itemSaleEntity1 = new ItemSaleEntity();
        itemSaleEntity1.setItem(new ItemIdEntity("itemId"));
        itemSaleEntity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        itemSaleEntity1.setPrice(1);

        ItemSaleEntity itemSaleEntity2 = new ItemSaleEntity();
        itemSaleEntity2.setItem(new ItemIdEntity("itemId"));
        itemSaleEntity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        assertEquals(itemSaleEntity1, itemSaleEntity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemSaleEntity itemSaleEntity = new ItemSaleEntity();
        assertNotEquals(null, itemSaleEntity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ItemSaleEntity itemSaleEntity1 = new ItemSaleEntity();
        itemSaleEntity1.setItem(new ItemIdEntity("itemId1"));
        itemSaleEntity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        ItemSaleEntity itemSaleEntity2 = new ItemSaleEntity();
        itemSaleEntity2.setItem(new ItemIdEntity("itemId2"));
        itemSaleEntity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        assertNotEquals(itemSaleEntity1, itemSaleEntity2);
    }

    @Test
    public void getItemId_should_return_item_itemId() {
        ItemIdEntity item = new ItemIdEntity();
        item.setItemId("itemId");
        ItemSaleEntity itemSaleEntity = new ItemSaleEntity();
        itemSaleEntity.setItem(item);
        assertEquals("itemId", itemSaleEntity.getItemId_());
    }
}