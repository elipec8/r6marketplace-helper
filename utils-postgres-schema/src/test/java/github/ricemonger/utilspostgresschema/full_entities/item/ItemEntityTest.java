package github.ricemonger.utilspostgresschema.full_entities.item;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemEntityTest {

    @Test
    public void hashCode_should_be_equal_for_equal_ids() {
        ItemEntity entity1 = new ItemEntity();
        entity1.setItemId("itemId");
        entity1.setAssetUrl("url");
        entity1.setName("name");
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(150);
        entity1.setMonthAveragePrice(120);
        entity1.setMonthMedianPrice(130);
        entity1.setMonthMaxPrice(140);
        entity1.setMonthMinPrice(110);
        entity1.setMonthSalesPerDay(5);
        entity1.setMonthSales(50);
        entity1.setDayAveragePrice(125);
        entity1.setDayMedianPrice(135);
        entity1.setDayMaxPrice(145);
        entity1.setDayMinPrice(115);
        entity1.setPriorityToSellByMaxBuyPrice(1L);
        entity1.setPriorityToSellByNextFancySellPrice(2L);
        entity1.setPriorityToBuyByMinSellPrice(3L);
        entity1.setPriorityToBuyIn1Hour(4L);
        entity1.setPriorityToBuyIn6Hours(5L);
        entity1.setPriorityToBuyIn24Hours(6L);
        entity1.setPriorityToBuyIn168Hours(7L);
        entity1.setPriorityToBuyIn720Hours(8L);
        entity1.setPriceToBuyIn1Hour(9);
        entity1.setPriceToBuyIn6Hours(10);
        entity1.setPriceToBuyIn24Hours(11);
        entity1.setPriceToBuyIn168Hours(12);
        entity1.setPriceToBuyIn720Hours(13);

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemEntity entity = new ItemEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemEntity entity1 = new ItemEntity();
        entity1.setItemId("itemId");
        entity1.setAssetUrl("url");
        entity1.setName("name");
        entity1.setTags(Collections.emptyList());
        entity1.setRarity(ItemRarity.RARE);
        entity1.setType(ItemType.WeaponSkin);
        entity1.setMaxBuyPrice(100);
        entity1.setBuyOrdersCount(10);
        entity1.setMinSellPrice(200);
        entity1.setSellOrdersCount(20);
        entity1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastSoldPrice(150);
        entity1.setMonthAveragePrice(120);
        entity1.setMonthMedianPrice(130);
        entity1.setMonthMaxPrice(140);
        entity1.setMonthMinPrice(110);
        entity1.setMonthSalesPerDay(5);
        entity1.setMonthSales(50);
        entity1.setDayAveragePrice(125);
        entity1.setDayMedianPrice(135);
        entity1.setDayMaxPrice(145);
        entity1.setDayMinPrice(115);
        entity1.setPriorityToSellByMaxBuyPrice(1L);
        entity1.setPriorityToSellByNextFancySellPrice(2L);
        entity1.setPriorityToBuyByMinSellPrice(3L);
        entity1.setPriorityToBuyIn1Hour(4L);
        entity1.setPriorityToBuyIn6Hours(5L);
        entity1.setPriorityToBuyIn24Hours(6L);
        entity1.setPriorityToBuyIn168Hours(7L);
        entity1.setPriorityToBuyIn720Hours(8L);
        entity1.setPriceToBuyIn1Hour(9);
        entity1.setPriceToBuyIn6Hours(10);
        entity1.setPriceToBuyIn24Hours(11);
        entity1.setPriceToBuyIn168Hours(12);
        entity1.setPriceToBuyIn720Hours(13);

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemEntity entity = new ItemEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_for_different_id() {
        ItemEntity entity = new ItemEntity();
        entity.setItemId("itemId");

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId2");

        assertNotEquals(entity, entity2);
    }
}