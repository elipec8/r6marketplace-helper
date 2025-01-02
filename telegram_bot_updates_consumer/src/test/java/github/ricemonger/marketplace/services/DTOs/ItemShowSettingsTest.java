package github.ricemonger.marketplace.services.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemShowSettingsTest {
    @Test
    public void constructor_should_properly_set_fields() {
        ItemShowSettings itemShowSettings = new ItemShowSettings(1, true, true, true, true, true, true, true, true, List.of());
        assertEquals(1, itemShowSettings.getItemShowMessagesLimit());
        assertTrue(itemShowSettings.getItemShowFewInMessageFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowNameFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowItemTypeFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowMaxBuyPrice());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowBuyOrdersCountFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowMinSellPriceFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemsShowSellOrdersCountFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().getItemShowPictureFlag());
        assertTrue(itemShowSettings.getItemShowAppliedFilters().isEmpty());
    }

    @Test
    public void getActiveFieldsCount_should_return_active_fields_count() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getActiveFieldsCount()).thenReturn(1);

        assertEquals(1, itemShowSettings.getActiveFieldsCount());
    }

    @Test
    public void getActiveFieldsCount_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getActiveFieldsCount());
    }

    @Test
    public void showItem_should_return_shownFieldsSettings_showItem() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Item item = Mockito.mock(Item.class);
        Mockito.when(shownFieldsSettings.showItem(item)).thenReturn("shownFields");

        assertEquals("shownFields", itemShowSettings.showItem(item));
    }

    @Test
    public void showItem_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.showItem(Mockito.mock(Item.class)));
    }

    @Test
    public void getItemShowNameFlag_should_return_shownFieldsSettings_ItemShowNameFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowNameFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowNameFlag());
    }

    @Test
    public void getItemShowNameFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowNameFlag());
    }

    @Test
    public void getItemShowItemTypeFlag_should_return_shownFieldsSettings_ItemShowItemTypeFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowItemTypeFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowItemTypeFlag());
    }

    @Test
    public void getItemShowItemTypeFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowItemTypeFlag());
    }

    @Test
    public void getItemShowMaxBuyPrice_should_return_shownFieldsSettings_ItemShowMaxBuyPrice() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowMaxBuyPrice()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowMaxBuyPrice());
    }

    @Test
    public void getItemShowMaxBuyPrice_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowMaxBuyPrice());
    }

    @Test
    public void getItemShowBuyOrdersCountFlag_should_return_shownFieldsSettings_ItemShowBuyOrdersCountFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowBuyOrdersCountFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowBuyOrdersCountFlag());
    }

    @Test
    public void getItemShowBuyOrdersCountFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowBuyOrdersCountFlag());
    }

    @Test
    public void getItemShowMinSellPriceFlag_should_return_shownFieldsSettings_ItemShowMinSellPriceFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowMinSellPriceFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowMinSellPriceFlag());
    }

    @Test
    public void getItemShowMinSellPriceFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowMinSellPriceFlag());
    }

    @Test
    public void getItemShowSellOrdersCountFlag_should_return_shownFieldsSettings_ItemsShowSellOrdersCountFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemsShowSellOrdersCountFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowSellOrdersCountFlag());
    }

    @Test
    public void getItemShowSellOrdersCountFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowSellOrdersCountFlag());
    }

    @Test
    public void getItemShowPictureFlag_should_return_shownFieldsSettings_ItemShowPictureFlag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        ItemShownFieldsSettings shownFieldsSettings = Mockito.mock(ItemShownFieldsSettings.class);
        itemShowSettings.setShownFieldsSettings(shownFieldsSettings);

        Mockito.when(shownFieldsSettings.getItemShowPictureFlag()).thenReturn(true);

        assertTrue(itemShowSettings.getItemShowPictureFlag());
    }

    @Test
    public void getItemShowPictureFlag_should_return_null_when_shownFieldsSettings_is_null() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setShownFieldsSettings(null);
        assertNull(itemShowSettings.getItemShowPictureFlag());
    }
}