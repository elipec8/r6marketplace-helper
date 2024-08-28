package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    public void getItemRarity_should_return_corresponding_rarity() {
        Item unknownNullTags = new Item();

        assertEquals(ItemRarity.UNKNOWN, unknownNullTags.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item unknownNoCoincidence = new Item();
        unknownNoCoincidence.setTags(List.of("tag"));

        assertEquals(ItemRarity.UNKNOWN, unknownNoCoincidence.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item uncommon = new Item();
        uncommon.setTags(List.of("uncommon"));

        assertEquals(ItemRarity.UNCOMMON, uncommon.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item rare = new Item();
        rare.setTags(List.of("rare"));

        assertEquals(ItemRarity.RARE, rare.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item epic = new Item();
        epic.setTags(List.of("epic"));

        assertEquals(ItemRarity.EPIC, epic.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item legendary = new Item();
        legendary.setTags(List.of("legendary"));

        assertEquals(ItemRarity.LEGENDARY, legendary.getItemRarity("uncommon", "rare", "epic", "legendary"));
    }

    @Test
    public void getItemRarity_should_return_lowest_rarity_if_multiple_rarity_tags() {
        Item uncommon = new Item();
        uncommon.setTags(List.of("uncommon", "rare", "epic", "legendary"));

        assertEquals(ItemRarity.UNCOMMON, uncommon.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item rare = new Item();
        rare.setTags(List.of("rare", "epic", "legendary"));

        assertEquals(ItemRarity.RARE, rare.getItemRarity("uncommon", "rare", "epic", "legendary"));

        Item epic = new Item();
        epic.setTags(List.of("epic", "legendary"));

        assertEquals(ItemRarity.EPIC, epic.getItemRarity("uncommon", "rare", "epic", "legendary"));
    }

    @Test
    public void toStringBySettings_should_print_proper_amount_of_fields() {
        ItemShownFieldsSettings settings = new ItemShownFieldsSettings();
        settings.setItemShowNameFlag(false);
        settings.setItemShowItemTypeFlag(false);
        settings.setItemShowMaxBuyPrice(false);
        settings.setItemShowBuyOrdersCountFlag(false);
        settings.setItemShowMinSellPriceFlag(false);
        settings.setItemsShowSellOrdersCountFlag(false);
        settings.setItemShowPictureFlag(false);

        Item item = new Item();
        assertEquals(1, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowNameFlag(true);
        assertEquals(2, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowItemTypeFlag(true);
        assertEquals(3, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowMaxBuyPrice(true);
        assertEquals(4, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowBuyOrdersCountFlag(true);
        assertEquals(5, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowMinSellPriceFlag(true);
        assertEquals(6, item.toStringBySettings(settings).split("\n").length);

        settings.setItemsShowSellOrdersCountFlag(true);
        assertEquals(7, item.toStringBySettings(settings).split("\n").length);

        settings.setItemShowPictureFlag(true);
        assertEquals(8, item.toStringBySettings(settings).split("\n").length);
    }
}