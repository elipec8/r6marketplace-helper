package github.ricemonger.utilspostgresschema.full_entities.item;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ItemSaleEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids(){
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(new ItemEntity());
        entity1.getItem().setItemId("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(100);
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(new ItemEntity());
        entity2.getItem().setItemId("itemId");
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setPrice(200);

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemSaleEntity entity = new ItemSaleEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(new ItemEntity());
        entity1.getItem().setItemId("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setPrice(100);
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(new ItemEntity());
        entity2.getItem().setItemId("itemId");
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity2.setPrice(200);

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemSaleEntity entity = new ItemSaleEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_id_field() {
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(new ItemEntity());
        entity1.getItem().setItemId("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(new ItemEntity());
        entity2.getItem().setItemId("itemId");
        entity2.setSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));

        entity1.getItem().setItemId("itemId2");
        assertNotEquals(entity1, entity2);
        entity1.getItem().setItemId("itemId");
        entity1.setSoldAt(LocalDateTime.of(2021, 1, 2, 0, 0));
        assertNotEquals(entity1, entity2);
    }
}