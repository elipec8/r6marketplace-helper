package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemRecalculationRequiredFieldsEntityTest {

    @Test
    public void hashCode_should_be_equal_for_equal_ids() {
        ItemRecalculationRequiredFieldsEntity entity1 = new ItemRecalculationRequiredFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setRarity(ItemRarity.UNCOMMON);
        entity1.setMaxBuyPrice(1);
        entity1.setMinSellPrice(2);
        entity1.setBuyOrdersCount(3);
        entity1.setSellOrdersCount(4);

        ItemRecalculationRequiredFieldsEntity entity2 = new ItemRecalculationRequiredFieldsEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemRecalculationRequiredFieldsEntity entity = new ItemRecalculationRequiredFieldsEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemRecalculationRequiredFieldsEntity entity1 = new ItemRecalculationRequiredFieldsEntity();
        entity1.setItemId("itemId");
        entity1.setRarity(ItemRarity.UNCOMMON);
        entity1.setMaxBuyPrice(1);
        entity1.setMinSellPrice(2);
        entity1.setBuyOrdersCount(3);
        entity1.setSellOrdersCount(4);

        ItemRecalculationRequiredFieldsEntity entity2 = new ItemRecalculationRequiredFieldsEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemRecalculationRequiredFieldsEntity entity = new ItemRecalculationRequiredFieldsEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ItemRecalculationRequiredFieldsEntity entity1 = new ItemRecalculationRequiredFieldsEntity();
        entity1.setItemId("itemId1");

        ItemRecalculationRequiredFieldsEntity entity2 = new ItemRecalculationRequiredFieldsEntity();
        entity2.setItemId("itemId2");

        assertNotEquals(entity1, entity2);
    }
}