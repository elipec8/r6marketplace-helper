package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemShownFieldsSettings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemSettingsTest {
    @Test
    public void getActiveFieldsCount_should_return_proper_count() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();

        assertEquals(1, settings.getActiveFieldsCount());

        settings.setItemShowNameFlag(true);
        assertEquals(2, settings.getActiveFieldsCount());

        settings.setItemShowItemTypeFlag(true);
        assertEquals(3, settings.getActiveFieldsCount());

        settings.setItemShowMaxBuyPrice(true);
        assertEquals(4, settings.getActiveFieldsCount());

        settings.setItemShowBuyOrdersCountFlag(true);
        assertEquals(5, settings.getActiveFieldsCount());

        settings.setItemShowMinSellPriceFlag(true);
        assertEquals(6, settings.getActiveFieldsCount());

        settings.setItemsShowSellOrdersCountFlag(true);
        assertEquals(7, settings.getActiveFieldsCount());

        settings.setItemShowPictureFlag(true);
        assertEquals(8, settings.getActiveFieldsCount());
    }
}