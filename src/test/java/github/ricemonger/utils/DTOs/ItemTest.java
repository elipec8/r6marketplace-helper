package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemHistoryFieldsI;
import github.ricemonger.utils.DTOs.items.ItemMainFieldsI;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_rare() {
        Item item = new Item();
        item.setTags(List.of("uncommon", "rare"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.RARE, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_epic() {
        Item item = new Item();
        item.setTags(List.of("uncommon", "rare", "epic"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.EPIC, item.getRarity());
    }

    @Test
    public void setRarityByTags_should_set_highest_rarity_if_contains_few_legendary() {
        Item item = new Item();
        item.setTags(List.of("uncommon", "rare", "epic", "legendary"));
        item.setRarityByTags("uncommon", "rare", "epic", "legendary");
        assertEquals(ItemRarity.LEGENDARY, item.getRarity());
    }

    @Test
    public void setMainFields_should_alter_main_fields() {
        Item item = new Item();

        ItemMainFieldsI mainFields = new Item();
        mainFields.setItemId("1");
        mainFields.setAssetUrl("url");
        mainFields.setName("name");
        mainFields.setTags(List.of("tag"));
        mainFields.setRarity(ItemRarity.UNCOMMON);
        mainFields.setType(ItemType.WeaponSkin);
        mainFields.setMaxBuyPrice(1);
        mainFields.setBuyOrdersCount(2);
        mainFields.setMinSellPrice(3);
        mainFields.setSellOrdersCount(4);
        mainFields.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        mainFields.setLastSoldPrice(5);
        item.setMainFields(mainFields);

        assertEquals("1", item.getItemId());
        assertEquals("url", item.getAssetUrl());
        assertEquals("name", item.getName());
        assertEquals(List.of("tag"), item.getTags());
        assertEquals(ItemRarity.UNCOMMON, item.getRarity());
        assertEquals(ItemType.WeaponSkin, item.getType());
        assertEquals(1, item.getMaxBuyPrice());
        assertEquals(2, item.getBuyOrdersCount());
        assertEquals(3, item.getMinSellPrice());
        assertEquals(4, item.getSellOrdersCount());
        assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1), item.getLastSoldAt());
        assertEquals(5, item.getLastSoldPrice());
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

    @Test
    public void hashCode_should_be_different_for_different_itemId() {
        Item item1 = new Item();
        item1.setItemId("1");

        Item item2 = new Item();
        item2.setItemId("2");

        assertNotEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    public void equals_should_be_false_for_different_itemId() {
        Item item1 = new Item();
        item1.setItemId("1");

        Item item2 = new Item();
        item2.setItemId("2");

        assertNotEquals(item1, item2);
    }

    @Test
    public void equals_should_be_false_for_null() {
        Item item1 = new Item();
        item1.setItemId("1");

        Item item2 = null;

        assertNotEquals(item2, item1);
    }

    @Test
    public void equals_should_be_false_for_object_of_another_class() {
        Item item1 = new Item();
        item1.setItemId("1");

        ItemFilter item2 = new ItemFilter();

        assertNotEquals(item2, item1);
    }
}