package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemSaleEntityTest {
    @Test
    public void isEqual_should_return_true_if_same() {
        ItemSaleEntity entity = new ItemSaleEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_ids() {
        ItemSaleEntity entity1 = new ItemSaleEntity("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(100);
        ItemSaleEntity entity2 = new ItemSaleEntity("itemId");
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setPrice(200);

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_if_null() {
        ItemSaleEntity entity = new ItemSaleEntity();
        assertFalse(entity.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_different_id_field() {
        ItemSaleEntity entity1 = new ItemSaleEntity("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        ItemSaleEntity entity2 = new ItemSaleEntity("itemId");
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        entity1.setItem(new ItemMainFieldsEntity("itemId1"));
        assertFalse(entity1.isEqual(entity2));
        entity1.setItem(new ItemMainFieldsEntity("itemId"));
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 2, 0, 0));
        assertFalse(entity1.isEqual(entity2));
    }

    @Test
    public void constructor_should_set_id_field() {
        ItemSaleEntity itemSaleEntity = new ItemSaleEntity("itemId");
        assertEquals("itemId", itemSaleEntity.getItemId_());
    }

    @Test
    public void getItemId_should_return_item_itemId() {
        ItemMainFieldsEntity item = new ItemMainFieldsEntity();
        item.setItemId("itemId");
        ItemSaleEntity itemSaleEntity = new ItemSaleEntity();
        itemSaleEntity.setItem(item);
        assertEquals("itemId", itemSaleEntity.getItemId_());
    }

    @Test
    public void isFullyEqual_should_return_true_if_same() {
        ItemSaleEntity entity = new ItemSaleEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        ItemMainFieldsEntity item = new ItemMainFieldsEntity();
        item.setItemId("itemId");

        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item);
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(100);

        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item);
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setPrice(100);

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        ItemMainFieldsEntity item1 = new ItemMainFieldsEntity();
        item1.setItemId("itemId1");

        ItemMainFieldsEntity item2 = new ItemMainFieldsEntity();
        item2.setItemId("itemId");

        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item1);
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(100);

        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item2);
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setPrice(100);

        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setItem(item2);
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 2, 0, 0));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(101);
        assertFalse(entity1.isFullyEqual(entity2));
    }
}