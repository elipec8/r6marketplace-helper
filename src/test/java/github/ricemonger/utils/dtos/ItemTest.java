package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {

    @Test
    public void setRarityByTags_should_set_rarity_to_unknown_when_tags_are_null() {
        Item item = new Item();
        item.setTags(null);
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNKNOWN, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_unknown_when_tags_contains_no_rarity() {
        Item item = new Item();
        item.setTags(List.of("unknown"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNKNOWN, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_uncommon_when_tags_contains_uncommon() {
        Item item = new Item();
        item.setTags(List.of("uncommon"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_rare_when_tags_contains_rare() {
        Item item = new Item();
        item.setTags(List.of("rare"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_epic_when_tags_contains_epic() {
        Item item = new Item();
        item.setTags(List.of("epic"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.EPIC, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_rarity_to_legendary_when_tags_contains_legendary() {
        Item item = new Item();
        item.setTags(List.of("legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.LEGENDARY, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_smallest_rarity_if_contains_few_uncommon() {
        Item item = new Item();
        item.setTags(List.of("uncommon", "rare", "epic", "legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_smallest_rarity_if_contains_few_rare() {
        Item item = new Item();
        item.setTags(List.of("rare", "epic", "legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_smallest_rarity_if_contains_few_epic() {
        Item item = new Item();
        item.setTags(List.of("epic", "legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.EPIC, item.getRarity());
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