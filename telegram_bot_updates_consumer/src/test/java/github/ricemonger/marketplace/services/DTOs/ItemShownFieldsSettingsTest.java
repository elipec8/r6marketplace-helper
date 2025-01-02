package github.ricemonger.marketplace.services.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
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

    @Test
    public void showItem_should_print_proper_amount_of_fields() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        ItemMainFieldsI item = new Item();
        assertEquals(1, settings.showItem(item).split("\n").length);

        settings.setItemShowNameFlag(true);
        assertEquals(2, settings.showItem(item).split("\n").length);

        settings.setItemShowItemTypeFlag(true);
        assertEquals(3, settings.showItem(item).split("\n").length);

        settings.setItemShowMaxBuyPrice(true);
        assertEquals(4, settings.showItem(item).split("\n").length);

        settings.setItemShowBuyOrdersCountFlag(true);
        assertEquals(5, settings.showItem(item).split("\n").length);

        settings.setItemShowMinSellPriceFlag(true);
        assertEquals(6, settings.showItem(item).split("\n").length);

        settings.setItemsShowSellOrdersCountFlag(true);
        assertEquals(7, settings.showItem(item).split("\n").length);

        settings.setItemShowPictureFlag(true);
        assertEquals(8, settings.showItem(item).split("\n").length);
    }
}