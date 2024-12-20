package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemShowSettings;
import github.ricemonger.utils.DTOs.personal.ItemShownFieldsSettings;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemShowSettingsTest {

    @Test
    public void constructor_should_properly_set_fields() {
        ItemShowSettings itemShowSettings = new ItemShowSettings(1, true, true, true, true, true, true, true, true, List.of());
        assertEquals(1, itemShowSettings.getItemShowMessagesLimit());
        assertTrue(itemShowSettings.isItemShowFewInMessageFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowNameFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowItemTypeFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowMaxBuyPrice());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowBuyOrdersCountFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowMinSellPriceFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemsShowSellOrdersCountFlag());
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowPictureFlag());
        assertTrue(itemShowSettings.getItemShowAppliedFilters().isEmpty());
    }

    @Test
    public void setItemShowNameFlag_should_set_name_flag() {
        ItemShowSettings itemShowSettings = new ItemShowSettings();
        itemShowSettings.setItemShowNameFlag(true);
        assertTrue(itemShowSettings.getShownFieldsSettings().isItemShowNameFlag());

        itemShowSettings.setItemShowNameFlag(false);
        assertFalse(itemShowSettings.getShownFieldsSettings().isItemShowNameFlag());
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
}