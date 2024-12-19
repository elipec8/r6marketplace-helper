package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemSaleEntityDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemSaleEntityTest {

    @Test
    public void toItemSaleEntity_should_properly_map_with_valid_fields() {
        ItemSaleEntityDTO sale = new ItemSaleEntityDTO();
        sale.setItemId("1");
        sale.setLastSoldAt(LocalDateTime.MIN);
        sale.setLastSoldPrice(2);

        ItemSaleEntity expected = new ItemSaleEntity();
        expected.setItem(new ItemEntity("1"));
        expected.setSoldAt(LocalDateTime.MIN);
        expected.setPrice(2);

        ItemSaleEntity actual = new ItemSaleEntity(sale);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSaleEntity_should_properly_map_with_null_fields() {
        ItemSaleEntityDTO sale = new ItemSaleEntityDTO();
        sale.setItemId(null);
        sale.setLastSoldAt(null);

        ItemSaleEntity expected = new ItemSaleEntity();
        expected.setItem(new ItemEntity());
        expected.setSoldAt(null);
        expected.setPrice(0);

        ItemSaleEntity actual = new ItemSaleEntity(sale);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSale_should_properly_map_with_valid_fields() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItem(new ItemEntity("1"));
        entity.setSoldAt(LocalDateTime.MAX);
        entity.setPrice(2);

        ItemSaleEntityDTO expected = new ItemSaleEntityDTO();
        expected.setItemId("1");
        expected.setSoldAt(LocalDateTime.MAX);
        expected.setPrice(2);

        ItemSaleEntityDTO actual = entity.toItemSale();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemSale_should_properly_map_with_null_fields() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItem(null);
        entity.setSoldAt(null);

        ItemSaleEntityDTO expected = new ItemSaleEntityDTO();
        expected.setItemId(null);
        expected.setSoldAt(null);
        expected.setPrice(0);

        ItemSaleEntityDTO actual = entity.toItemSale();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemSaleEntity entity1, ItemSaleEntity entity2) {
        return (entity1.getItem() == entity2.getItem() || (entity1.getItem() != null &&
                                                           Objects.equals(entity1.getItem().getItemId(), entity2.getItem().getItemId()))) &&
               Objects.equals(entity1.getSoldAt(), entity2.getSoldAt()) &&
               entity1.getPrice() == entity2.getPrice();
    }
}