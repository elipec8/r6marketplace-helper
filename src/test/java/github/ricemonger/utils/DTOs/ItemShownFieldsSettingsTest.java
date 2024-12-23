package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemShownFieldsSettings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemShownFieldsSettingsTest {

    @Test
    public void getActiveFieldsCount_should_return_count_of_true_fields() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(true);
        settings.setItemShowItemTypeFlag(true);
        settings.setItemShowMaxBuyPrice(true);
        settings.setItemShowBuyOrdersCountFlag(true);
        settings.setItemShowMinSellPriceFlag(true);
        settings.setItemsShowSellOrdersCountFlag(true);
        settings.setItemShowPictureFlag(true);
        assertEquals(8, settings.getActiveFieldsCount());

        settings.setItemShowNameFlag(false);
        assertEquals(7, settings.getActiveFieldsCount());

        settings.setItemShowItemTypeFlag(false);
        assertEquals(6, settings.getActiveFieldsCount());

        settings.setItemShowMaxBuyPrice(false);
        assertEquals(5, settings.getActiveFieldsCount());

        settings.setItemShowBuyOrdersCountFlag(false);
        assertEquals(4, settings.getActiveFieldsCount());

        settings.setItemShowMinSellPriceFlag(false);
        assertEquals(3, settings.getActiveFieldsCount());

        settings.setItemsShowSellOrdersCountFlag(false);
        assertEquals(2, settings.getActiveFieldsCount());

        settings.setItemShowPictureFlag(false);
        assertEquals(1, settings.getActiveFieldsCount());
    }
}