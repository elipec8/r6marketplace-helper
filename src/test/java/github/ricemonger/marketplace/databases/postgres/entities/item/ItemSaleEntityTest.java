package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.ItemSale;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemSaleEntityTest {

    @Test
    public void toItemSaleEntity_should_properly_map_with_valid_fields() {
        ItemSale sale = new ItemSale();
        sale.setItemId("1");
        sale.setLastSoldAt(new Date(0));
        sale.setLastSoldPrice(2);

        ItemSaleEntity expected = new ItemSaleEntity();
        expected.setItemId("1");
        expected.setSoldAt(new Date(0));
        expected.setPrice(2);

        ItemSaleEntity actual = new ItemSaleEntity(sale);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSaleEntity_should_properly_map_with_null_fields() {
        ItemSale sale = new ItemSale();
        sale.setItemId(null);
        sale.setLastSoldAt(null);

        ItemSaleEntity expected = new ItemSaleEntity();
        expected.setItemId(null);
        expected.setSoldAt(null);
        expected.setPrice(0);

        ItemSaleEntity actual = new ItemSaleEntity(sale);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemSale_should_properly_map_with_valid_fields() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItemId("1");
        entity.setSoldAt(new Date(0));
        entity.setPrice(2);

        ItemSale expected = new ItemSale();
        expected.setItemId("1");
        expected.setSoldAt(new Date(0));
        expected.setPrice(2);

        ItemSale actual = entity.toItemSale();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemSale_should_properly_map_with_null_fields() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItemId(null);
        entity.setSoldAt(null);

        ItemSale expected = new ItemSale();
        expected.setItemId(null);
        expected.setSoldAt(null);
        expected.setPrice(0);

        ItemSale actual = entity.toItemSale();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemSaleEntity entity1, ItemSaleEntity entity2) {
        return Objects.equals(entity1.getItemId(), entity2.getItemId()) &&
               Objects.equals(entity1.getSoldAt(), entity2.getSoldAt()) &&
               entity1.getPrice() == entity2.getPrice();
    }
}