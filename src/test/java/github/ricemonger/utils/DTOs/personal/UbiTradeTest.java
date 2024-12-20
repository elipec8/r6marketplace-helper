package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.common.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UbiTradeTest {

    @Test
    public void getItemId_should_return_item_itemId() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(new Item("itemId"));
        assertEquals("itemId", ubiTrade.getItemId());
    }

    @Test
    public void getItemId_should_return_null_if_item_is_null() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(null);
        assertNull(ubiTrade.getItemId());
    }

    @Test
    public void getItemName_should_return_item_itemName() {
        UbiTrade ubiTrade = new UbiTrade();
        Item item = new Item();
        item.setName("itemName");
        ubiTrade.setItem(item);
        assertEquals("itemName", ubiTrade.getItemName());
    }

    @Test
    public void getItemName_should_return_null_if_item_is_null() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(null);
        assertNull(ubiTrade.getItemName());
    }
}